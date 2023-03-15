import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SenFileCompressor {
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        String option = args[0];
        switch (option) {
            case "-h":
                printUsage();
                break;
            case "-c":
                String[] filesToCompress = parseFilesToCompress(args);
                String outputFilePath = parseOutputFilePath(args);
                String directoryPath = parseDirectoryPath(args);
                boolean forceCreateDirectory = parseForceCreateDirectory(args);
                boolean verbose = parseVerbose(args);
                compressFiles(filesToCompress, outputFilePath, directoryPath, forceCreateDirectory, verbose);
                break;
            case "-d":
                String filePath = parseInputFilePath(args);
                directoryPath = parseDirectoryPath(args);
                forceCreateDirectory = parseForceCreateDirectory(args);
                verbose = parseVerbose(args);
                decompressFile(filePath, directoryPath, forceCreateDirectory, verbose);
                break;
            default:
                System.err.println("Invalid option: " + option);
                printUsage();
                System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java SenFileCompressor [options]");
        System.out.println("Options:");
        System.out.println("-h\t\t\tShow this help message");
        System.out.println("-c <files to compress>\tCompress specified files into an archive");
        System.out.println("-d <file to decompress>\tDecompress specified archive into files");
        System.out.println("Optional parameters:");
        System.out.println("-r <directory path>\t\tSpecify directory path for output files");
        System.out.println("-f\t\t\tForce create directory path if it doesn't exist");
        System.out.println("-v\t\t\tVerbose output");
    }

    private static String[] parseFilesToCompress(String[] args) {
        if (args.length < 2) {
            System.err.println("Missing files to compress");
            printUsage();
            System.exit(1);
        }
        String[] files = new String[args.length - 1];
        System.arraycopy(args, 1, files, 0, args.length - 1);
        return files;
    }

    private static String parseOutputFilePath(String[] args) {
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-o")) {
                if (i == args.length - 1) {
                    System.err.println("Missing output file path");
                    printUsage();
                    System.exit(1);
                }
                return args[i + 1];
            }
        }
        return null;
    }

    private static String parseInputFilePath(String[] args) {
        if (args.length < 2) {
            System.err.println("Missing input file path");
            printUsage();
            System.exit(1);
        }
        return args[1];
    }

    private static String parseDirectoryPath(String[] args) {
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-r")) {
                if (i == args.length - 1) {
                    System.err.println("Missing directory path");
                    printUsage();
                    System.exit(1);
                }
                return args[i + 1];
            }
        }
        return null;
    }

    private static boolean parseForceCreateDirectory(String[] args) {
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-f")) {
                return true;
            }
        }
        return
