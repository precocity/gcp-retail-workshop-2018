package com.precocityllc.demo.gcp.sales.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.precocityllc.demo.gcp.common.cli.BaseCLIApp;
import com.precocityllc.demo.gcp.converters.sales.model.SalesHeader;
import com.precocityllc.demo.gcp.converters.sales.model.SalesRecord;
import com.precocityllc.demo.gcp.sales.publisher.stream.PubSubPublisher;
import com.precocityllc.demo.gcp.sales.publisher.util.RecordFileLineOffset;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SalesDataPublisher extends BaseCLIApp implements Runnable {
    private Logger log = LogManager.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();
    private int maxBatchSize = 100;
    private int delayBetweenBatchesInMs = 100;
    private String pubSubTopic = "";
    private String inputFile = "";
    private String offsetFile = "offset";
    private DateFormat timestampDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private boolean running;
    private boolean publishing;

    public SalesDataPublisher() {
    }

    public SalesDataPublisher(int maxBatchSize, int delayBetweenBatchesInMs, String pubSubTopic, String inputFile, String offsetFile) {
        this.maxBatchSize = maxBatchSize;
        this.delayBetweenBatchesInMs = delayBetweenBatchesInMs;
        this.pubSubTopic = pubSubTopic;
        this.inputFile = inputFile;
        this.offsetFile = offsetFile;
    }

    public static void main(String... args) {
        SalesDataPublisher app = new SalesDataPublisher();
        app.go(args);
    }

    private void go(String... args) {
        log.info("Starting processing");
        try {
            processCommandLine(args);
            startThread();
        } catch (Exception e) {
            log.error("error:", e);
        }
    }


    public void startThread() {
        final SalesDataPublisher sdg = new SalesDataPublisher(maxBatchSize, delayBetweenBatchesInMs, pubSubTopic, inputFile, offsetFile);
        final Thread sdgThread = new Thread(sdg);

        sdgThread.start();
        sdg.setRunning(true);

        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                while (sdg.isPublishing()) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        log.error("error", e);
                    }
                }
                sdgThread.interrupt();
                sdg.setRunning(false);
                try {
                    mainThread.join();
                } catch (InterruptedException ex) {
                    //terminate
                }
            }
        });

        while (sdg.isRunning()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                //terminate
            }
        }


    }

    private BufferedReader openAndFastForwardFile(String fileToRead, int linesToSkip) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inputFile)));
        for (int i = 0; i < linesToSkip; i++) {
            bufferedReader.readLine();
        }
        return bufferedReader;
    }

    /**
     * Convert the line from the file into an object and prepare it to send.
     *
     * @param line
     * @return
     * @throws Exception
     */
    private SalesRecord readRecordFromFile(String line) throws Exception {
        SalesRecord record = objectMapper.readValue(line, SalesRecord.class);
        record.setUpdatedTimestamp(timestampDateFormat.format(new Date()));
        return record;
    }


    public void run() {
        Random random = new SecureRandom();
        BufferedReader bufferedReader = null;
        PubSubPublisher pubSubPublisher = null;
        RecordFileLineOffset offsetRecorder = new RecordFileLineOffset();
        offsetRecorder.setOffsetFile( offsetFile );
        try {
            pubSubPublisher = new PubSubPublisher(pubSubTopic);
            int offset = offsetRecorder.getLastReadLine();
            boolean readMoreLines = true;

            log.info("opening {} for reading. Skipping {} lines.", inputFile, offset);
            bufferedReader = openAndFastForwardFile(inputFile, offset);
            List<String> recordsToSend = new ArrayList<>();

            while (readMoreLines) {
                recordsToSend.clear();
                publishing = true;
                int linesToRead = random.nextInt(maxBatchSize);
                int linesThisBatch = 0;
                log.info("sending {} lines to pubsub", linesToRead);

                // Build up the records to send.
                for (int i = 0; i < linesToRead; i++) {
                    String line = bufferedReader.readLine();
                    if (null == line) {
                        readMoreLines = false;
                        break;
                    } else {
                        linesThisBatch++;
                        recordsToSend.add(objectMapper.writeValueAsString(readRecordFromFile(line)));
                    }
                }

                // publish to pubsub
                if (linesThisBatch > 0) pubSubPublisher.publish(recordsToSend);

                // Tell the thread handler we are not publishing.
                publishing = false;

                //last step to save sequence
                // This MAY NOT BE ACCURATE AS EXCEPTION OR INTERRUPTION MAY BREAK UP BATCH SEND
                offset += linesThisBatch;
                offsetRecorder.saveLastReadLine(offset);

                // Sleep for a bit
                Thread.sleep(delayBetweenBatchesInMs);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            publishing = false;
            running = false;
            try {
                if (null != bufferedReader) bufferedReader.close();
            } catch (Exception e) {
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isPublishing() {
        return publishing;
    }

    public void setPublishing(boolean publishing) {
        this.publishing = publishing;
    }


    /**
     * Reads in the program arguments
     *
     * @param args
     * @throws Exception
     */
    private void processCommandLine(String... args) throws Exception {

        // log arguments
        logCommandLineArguments(args);

        log.info("Parsing command line options.");
        Options options = new Options();
        try {
            // Required parameters
            options.addOption(OptionBuilder.withLongOpt("batch_delay").withArgName("batch delay in ms").hasArgs(2).isRequired(true).withDescription("batch delay in ms").create("d"));
            options.addOption(OptionBuilder.withLongOpt("max_batch_size").withArgName("max batch size").hasArgs(2).isRequired(true).withDescription("max batch size").create("s"));
            options.addOption(OptionBuilder.withLongOpt("pub_sub_topic").withArgName("pub_sub_topic").hasArgs(2).isRequired(true).withDescription("pubsub topic").create("t"));
            options.addOption(OptionBuilder.withLongOpt("input_file").withArgName("input_file").hasArgs(2).isRequired(true).withDescription("input file").create("i"));
            options.addOption(OptionBuilder.withLongOpt("offset_file").withArgName("offset_file").hasArgs(2).isRequired(false).withDescription("offset file").create("o"));

            // parse the args
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);

            // Check the options
            if (cmd.hasOption("help")) {
                printUsage(options);
            }

            if (cmd.hasOption("d")) {
                delayBetweenBatchesInMs = Integer.parseInt(cmd.getOptionValue("d"));
            }
            if (cmd.hasOption("s")) {
                maxBatchSize = Integer.parseInt(cmd.getOptionValue("s"));
            }
            if (cmd.hasOption("t")) {
                pubSubTopic = cmd.getOptionValue("t");
            }
            if (cmd.hasOption("i")) {
                inputFile = cmd.getOptionValue("i");
            }

            if (cmd.hasOption("o")) {
                offsetFile = cmd.getOptionValue("o");
            }

        } catch (ParseException exp) {
            log.error("Error parsing options: " + exp.getMessage());
            printUsage(options);
        }
        log.info("Completed parsing command line options.");

    }

}
