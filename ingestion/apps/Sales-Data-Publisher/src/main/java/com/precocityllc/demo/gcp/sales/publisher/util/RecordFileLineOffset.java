package com.precocityllc.demo.gcp.sales.publisher.util;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordFileLineOffset {
    private static final String ENCODING = "UTF-8";
    private static String OFFSETFILE = "offset";
    private static Logger log = LogManager.getLogger(RecordFileLineOffset.class);


    public void setOffsetFile(String filename) {
        OFFSETFILE = filename;
    }

    /**
     * Reads the offset stored in the offset file
     * defaults to line 0
     *
     * @return
     */
    public int getLastReadLine() {
        int line = 0;

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(OFFSETFILE)));
            line = Integer.parseInt(bufferedReader.readLine());
        } catch (FileNotFoundException fnf) {
            // this is OK = start at default
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            try {
                if (null != bufferedReader) bufferedReader.close();
            } catch (Exception e) {
            }
        }

        return line;
    }

    /**
     * Writes the line number to an offset file
     *
     * @param line
     */
    public void saveLastReadLine(int line) {
        BufferedWriter writer = null;
        try {
            writer =
                    Files.newBufferedWriter(
                            FileSystems.getDefault().getPath(OFFSETFILE),
                            Charset.forName(ENCODING),
                            StandardOpenOption.CREATE);
            String content = Integer.toString(line);
            writer.write(content, 0, content.length());
            writer.flush();
        } catch (IOException e) {
            log.error("error", e);
        } finally {
            try {
                if (null != writer)
                    writer.close();
            } catch (Exception e) {
            }
        }
    }
}