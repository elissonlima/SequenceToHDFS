package me.elisson;

import me.elisson.hdfs.HDFSWriter;
import me.elisson.reader.fasta.FASTAReader;
import me.elisson.splitter.SequenceSplitter;
import java.io.FileNotFoundException;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        long blockSize = 128 * 1024 * 1024L;
        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            FASTAReader reader = new FASTAReader(inputFilePath);
            SequenceSplitter splitter = new SequenceSplitter(reader, blockSize);
            HDFSWriter writer = new HDFSWriter(outputFilePath);

            long start = System.currentTimeMillis();
            long elapsed = 0L;
            long fileSizeInBytes = getFileSizeNio(inputFilePath);
            long totalBytesWritten = 0L;
            long totalBytesWrittenInCycle = 0L;

            while (splitter.hasNext()) {
                writer.writeBlock(splitter.next());

                //Calculate Progress
                elapsed += System.currentTimeMillis() - start;
                totalBytesWritten += blockSize;
                totalBytesWrittenInCycle += blockSize;
                if (elapsed > 1000L) {
                    WriteProgressPrinter.print(
                            elapsed,
                            totalBytesWrittenInCycle,
                            fileSizeInBytes,
                            totalBytesWritten);
                    totalBytesWrittenInCycle = 0L;
                    elapsed = 0L;
                }
            }

        } catch (FileNotFoundException ex) {
            System.err.println("Input file not found: " + inputFilePath);
            ex.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException capture: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }


    public static long getFileSizeNio(String filePath) throws IOException {
        // 1. Create a Path object from the file path string
        java.nio.file.Path path = java.nio.file.Paths.get(filePath);

        // 2. Use Files.size() to get the size
        // This method handles checks for existence and ensures it's a regular file.
        // It throws exceptions like NoSuchFileException, AccessDeniedException, etc.
        // (all subclasses of IOException) if there's an issue.
        long sizeInBytes = java.nio.file.Files.size(path);

        return sizeInBytes;
    }
}