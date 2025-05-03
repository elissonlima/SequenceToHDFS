package junit;

import me.elisson.reader.fasta.FASTAObject;
import me.elisson.reader.fasta.FASTAReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FASTAReaderTests {

    private FASTAReader reader;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        reader = new FASTAReader("./src/test/java/junit/assets/example.fasta");
    }

    @DisplayName("Test Count All Sequences in FASTA File")
    @Test
    public void testReadAllSequences() {
        int count = 0;
        while(reader.hasNext()) {
            reader.next();
            count += 1;
        }
        assertEquals(3, count);
    }

    @DisplayName("Test Get All Sequence IDs")
    @Test
    public void testGetAllSequenceIDs() {
        List<String> ids = new ArrayList<>();
        while(reader.hasNext()) {
            FASTAObject obj = reader.next();
            ids.add(obj.getId());
        }
        assertEquals(3, ids.size());
        assertTrue(ids.stream().anyMatch(e -> e.equals(">NC_083851.1 |Bird's-foot trefoil nucleorhabdovirus isolate LC, complete genome\n")));
        assertTrue(ids.stream().anyMatch(e -> e.equals(">NC_077216.1 |Pepper yellows virus genome assembly, complete genome: monopartite\n")));
        assertTrue(ids.stream().anyMatch(e -> e.equals(">NC_077680.1 |Tomato mottle leaf curl virus isolate BR:Jai13:08 segment DNA-A, complete sequence\n")));
    }

    @DisplayName("Test Get All Sequences")
    @Test
    public void testGetAllSequences() {
        List<String> sequences = new ArrayList<>();
        while(reader.hasNext()) {
            FASTAObject obj = reader.next();
            sequences.add(obj.getSequence());
        }
        assertEquals(3, sequences.size());
        String sequence1 = "CACAATATACATGATGCTCGAACTGTTGGCCTATAAGCCTGCAAACCGCTCGGACTGTCG" +
                "TTTTTTGGAACTCTGATTATATAAGAAAAACCTTTTAATCACGAGGGAGATCCGAGGGG" +
                "CGTGGTGGCTCCAGGGGACACGGGGAGTCAGAGGATTGTCGGAAACCTTCAGCACCCTAA" +
                "CCGTTTATCCCTCTGGCCAAGAGGGATCATAGTTTATTGACAAGAGTGGATAAATCCACA" +
                "TCAAGC";
        String sequence2 = "ACAAAATATACGAAGAGAGAGAGCCCTTGCTAGTGATTTCTTCAAGTCTTATGAACTTTG" +
                "AATTGATCGACGGAAGCCATCTGAAAGTTTCCCTCACTCGCAAGCTCGGTTACAGAGAGA" +
                "GAATCCTAAATTTAGCAGTATTCTTAAGCCAATACCTCGTAACCGTACAAGAAAATGCAA" +
                "GGGGTGTATAGATACCCCCCCCTGGTCTATGATGT";
        String sequence3 = "ACCGGATGGCCGCGCGGGTTTTTTTGACCCGCTCCGTGATGTATTTTTTGTCTTTTACTA" +
                "TGTGGTCCAGTCAATAAATGACAAATATGACCGTCCAATCAGAAATGGTCCTCAAAGCCT" +
                "AATTATTTAAAAATACTTGGTCACTAAGTTTGGTAAAGTTTATAAATGATCCTTCCTCGT" +
                "AATGTTATACCAACTTTAAGTCATAATGCCTAAGCGTGATGCCCCATGGCGCTCAATGGC" +
                "GGGGACCTCAAAGGTTAGTCGGGCCGCCAATTTCTCCCCTCGTGGAGGAATCGGGTCGAA" +
                "A";
        assertTrue(sequences.stream().anyMatch(e -> e.equals(sequence1)));
        assertTrue(sequences.stream().anyMatch(e -> e.equals(sequence2)));
        assertTrue(sequences.stream().anyMatch(e -> e.equals(sequence3)));
    }

    @DisplayName("Test Calculate Total Size")
    @Test
    public void testCalculateTotalSize() {
        long totalSize = 0L;
        while(reader.hasNext()) {
            FASTAObject obj = reader.next();
            totalSize += obj.getSize();
        }
        assertEquals(1022L, totalSize);
    }

}
