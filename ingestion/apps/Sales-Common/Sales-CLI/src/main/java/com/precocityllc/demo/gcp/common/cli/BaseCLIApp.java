package com.precocityllc.demo.gcp.common.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseCLIApp {
    private Logger log = LogManager.getLogger(getClass());

    /**
     * Utility method to log the command line arguments
     *
     * @param args
     */
    protected void logCommandLineArguments(String... args) {
        StringBuilder arguments = new StringBuilder();
        boolean firstArg = true;
        for (String s : args) {
            if (!firstArg) arguments.append(" ");
            arguments.append(s);
            firstArg = false;
        }
        log.info("started with arguments: \"{}\"", arguments.toString());
    }

    /**
     * Print Usage
     *
     * @param options
     */
    protected void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        String header = "\n";
        String footer = "\n";
        formatter.printHelp("app", header, options, footer, true);
        System.exit(1);
    }
}
