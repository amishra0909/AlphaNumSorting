import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

// Driving main class
public class HumanReadableSort {

    private final static Options OPTIONS = new Options();

    static {
        OPTIONS.addOption("input", true, "list of names of input files");
        OPTIONS.addOption("output", true, "name of output file");
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            printUsageHelp();
            return;
        }

        // parse command line
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cli = parser.parse(OPTIONS, args);
            String inputFile = cli.getOptionValue("input");
            String outputFile = cli.getOptionValue("output");
            if (inputFile == null) {
                System.out.println("ERROR: no input file found.");
                printUsageHelp();
                return;
            }

            // read lines from input file
            FileReader inFile = new FileReader(inputFile);
            BufferedReader reader = new BufferedReader(inFile);
            String line = null;
            List<String> strList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
            	if (line != null) {
                    strList.add(line);
            	}
            }
            Collections.sort(strList, new AlphaNumComparator());

            FileWriter outFile = null;
            BufferedWriter writer = null;
            if (outputFile != null) {
            	outFile = new FileWriter(outputFile);
            	writer = new BufferedWriter(outFile);
            }

            for (String entry : strList) {
                if (writer != null) {
                    writer.write(entry + "\n");
                }
                System.out.println(entry);
            }
            
            // close io resources
            reader.close();
            inFile.close();
            if (writer != null) {
                writer.close();
            }
            if (outFile != null) {
                outFile.close();
            }
        } catch (ParseException e) {
            System.out.println("ERROR: not able to parse arguments");
            printUsageHelp();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR: not able to read from input file or write to output file.");
            System.out.println(e.getMessage());
        }
    }

    private static void printUsageHelp() {
        System.out.println(
            "This program is used to sort lines from input file in human readable form.\n" +
            "USAGE: \n" +
            "1. \"HumanReadableSort --input <input_file_name>\" sort lines of input file and prints the result to console. \n" +
            "2. \"HumanReadableSort --input <input_file_name> --output <output_file_name>\" sort lines of input file, " +
                "saves the result into output file and print the result to console.");
    }
}
