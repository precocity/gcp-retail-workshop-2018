package com.precocityllc.demo.gcp.converters.sales;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.precocityllc.demo.gcp.common.cli.BaseCLIApp;
import com.precocityllc.demo.gcp.converters.sales.model.SalesHeader;
import com.precocityllc.demo.gcp.converters.sales.model.SalesLineItem;
import com.precocityllc.demo.gcp.converters.sales.model.SalesRecord;
import org.apache.commons.cli.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SalesHeaderLineItemConverter extends BaseCLIApp{
    private Logger log = LogManager.getLogger(getClass());
    private String headerFile = "";
    private String lineItemFile = "";
    private String outputFile = "";
    private final char newline = '\n';

    public static void main(String... args) {
        SalesHeaderLineItemConverter app = new SalesHeaderLineItemConverter();
        app.go(args);
    }

    private void go(String... args) {
        log.info("Starting processing");
        try {

            processCommandLine(args);

            // Convert files to CSV Records
            Iterable<CSVRecord> headers = convertFileToRecords(headerFile);
            Iterable<CSVRecord> lineItems = convertFileToRecords(lineItemFile);

            // We need to convert CSV to POJO
            Map<Integer, SalesRecord> salesRecordMap = convertSalesHeaders(headers);

            // Add in lineItem data
            convertAndAddLineItemData(salesRecordMap, lineItems);

            // write the output data
            writeOutput(salesRecordMap);

        } catch (Exception e) {
            log.error("error:", e);
        }
    }

    /**
     * @param salesHeaderMap
     */
    private void writeOutput(Map<Integer, SalesRecord> salesHeaderMap) {
        log.info("Writing output to {}", outputFile);
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, false));

            for (Integer orderId : salesHeaderMap.keySet()) {
                writer.append(sanitizeLine(objectMapper.writeValueAsString(salesHeaderMap.get(orderId))));
                writer.append(newline);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            try {
                if (null != writer) writer.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }

    }

    /**
     * Utility to clean line before writing
     *
     * @param input
     * @return
     */
    private String sanitizeLine(String input) {
        return input.replaceAll("\n", "");
    }

    /**
     * Converting sales line items and will add them to the list maintained as part of the header record.
     *
     * @param salesRecords
     * @param lineItemRecords
     * @throws Exception
     */
    private void convertAndAddLineItemData(Map<Integer, SalesRecord> salesRecords, Iterable<CSVRecord> lineItemRecords) throws Exception {
        log.info("converting and adding sales line item data to sales header");
        if (null != lineItemRecords) {
            for (CSVRecord record : lineItemRecords) {
                SalesLineItem lineItem = new SalesLineItem(
                        Integer.parseInt(record.get("line_item_id")),
                        Integer.parseInt(record.get("order_id")),
                        Integer.parseInt(record.get("item_id")),
                        record.get("transaction_type"),
                        Integer.parseInt(record.get("quantity")),
                        record.get("item_price_each").equals("NULL") ? null : new BigDecimal(record.get("item_price_each")),
                        record.get("item_tax_each").equals("NULL") ? null : new BigDecimal(record.get("item_tax_each")),
                        record.get("markdown_each").equals("NULL") ? null : new BigDecimal(record.get("markdown_each")),
                        record.get("freight_each").equals("NULL") ? null : new BigDecimal(record.get("freight_each")),
                        record.get("duty_each").equals("NULL") ? null : new BigDecimal(record.get("duty_each")),
                        Integer.parseInt(record.get("gift_wrap_flag")),
                        record.get("item_status"),
                        record.get("item_status_as_of_date"),
                        record.get("ship_date"),
                        record.get("promotion_code"),
                        record.get("gift_message"),
                        record.get("gift_registry_number"),
                        record.get("created_timestamp"),
                        record.get("updated_timestamp")
                );

                // Add this lineItem to the appropriate header
                salesRecords.get(lineItem.getOrderId()).getLineItems().add(lineItem);
            }
        }

    }


    /**
     * Converts to PoJo map keyed on order id
     *
     * @param headerRecords
     * @return
     * @throws Exception
     */
    private Map<Integer, SalesRecord> convertSalesHeaders(Iterable<CSVRecord> headerRecords) throws Exception {
        log.info("converting sales header data");
        Map<Integer, SalesRecord> salesRecordMap = new HashMap<>();
        if (null != headerRecords) {
            for (CSVRecord record : headerRecords) {
                SalesHeader header = new SalesHeader(
                        Integer.parseInt(record.get("order_id"))
                        , Integer.parseInt(record.get("channel_id"))
                        , Integer.getInteger(record.get("customer_id"))
                        , Integer.getInteger(record.get("primary_associate_id"))
                        , Integer.getInteger(record.get("secondary_associate_id"))
                        , record.get("total_amount").equals("NULL") ? null : new BigDecimal(record.get("total_amount"))
                        , Integer.getInteger(record.get("store_id"))
                        , Integer.getInteger(record.get("pos_terminal_id"))
                        , record.get("transaction_date")
                        , record.get("transaction_number")
                        , record.get("merchandise_amount").equals("NULL") ? null : new BigDecimal(record.get("merchandise_amount"))
                        , record.get("tax_amount").equals("NULL") ? null : new BigDecimal(record.get("tax_amount"))
                        , record.get("payment_type")
                        , record.get("created_timestamp")
                        , record.get("updated_timestamp")
                );
                salesRecordMap.put(header.getOrderId(), new SalesRecord(header));
            }
        }

        return salesRecordMap;
    }

    /**
     * Utility method to read in a file and convert to records.
     *
     * @param filename
     * @return
     * @throws Exception
     */
    private Iterable<CSVRecord> convertFileToRecords(String filename) throws Exception {
        log.info("converting {} to CSV record ", filename);
        Reader in = new FileReader(filename);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
        return records;
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
            options.addOption(OptionBuilder.withLongOpt("sales_header_file").withArgName("sales header file").hasArgs(2).isRequired(true).withDescription("header file").create("h"));
            options.addOption(OptionBuilder.withLongOpt("sales_line_item_file").withArgName("salesline item file").hasArgs(2).isRequired(true).withDescription("line item file").create("l"));
            options.addOption(OptionBuilder.withLongOpt("output_file").withArgName("output file location").hasArgs(2).isRequired(true).withDescription("output file location").create("o"));

            // parse the args
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);

            // Check the options
            if (cmd.hasOption("help")) {
                printUsage(options);
            }

            if (cmd.hasOption("l")) {
                lineItemFile = cmd.getOptionValue("l");
            }
            if (cmd.hasOption("h")) {
                headerFile = cmd.getOptionValue("h");
            }
            if (cmd.hasOption("o")) {
                outputFile = cmd.getOptionValue("o");
            }

        } catch (ParseException exp) {
            log.error("Error parsing options: " + exp.getMessage());
            printUsage(options);
        }
        log.info("Completed parsing command line options.");

    }



}
