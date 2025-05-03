package junit;

import me.elisson.Main;
import me.elisson.WriteProgressPrinter;
import org.junit.jupiter.api.Test;

public class WriteProgressTest {

    @Test
    public void testPrintProgress() {
        try {
            long blockSize = 128 * 1024 * 1024L;
            long totalSize = 1 * 1024 * 1024 * 1024L;
            long start = System.currentTimeMillis();
            long totalBytesWritten = 0L;

            //while (true) {
                Thread.sleep(1000);
                long elapsed = System.currentTimeMillis() - start;
                totalBytesWritten += blockSize;
                WriteProgressPrinter.print(
                        elapsed,
                        blockSize,
                        totalSize,
                        totalBytesWritten
                );



//                if (totalBytesWritten >= totalSize) {
//                    break;
//                }
            //}
        } catch (Exception e) {
            System.err.println("Error on test");
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFileSize() {
        try {
            long total = Main.getFileSizeNio("./src/test/java/junit/assets/example2.fasta");
            //System.out.println(String.format("Total size: %dB", total));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
