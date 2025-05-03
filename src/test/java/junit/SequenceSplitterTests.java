package junit;

import me.elisson.reader.fasta.FASTAReader;
import me.elisson.splitter.SequenceSplitter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SequenceSplitterTests {

    private FASTAReader reader;
    private SequenceSplitter splitter;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        reader = new FASTAReader("./src/test/java/junit/assets/example2.fasta");
        splitter = new SequenceSplitter(reader, 2 * 1024L);
    }

    @AfterAll
    public static void cleanup() {

    }

    @DisplayName("Test Count All Sequences in FASTA File")
    @Test
    public void testReadAllSequences() {
        int count = 0;

        while (splitter.hasNext()) {
            String outFilePath = String.format("./src/test/java/junit/assets/out/out_%d.fasta", count);
            String outSplit = splitter.next();
            try (BufferedWriter w = new BufferedWriter(new FileWriter(outFilePath))) {
                w.write(outSplit);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            count += 1;
        }
    }

}
