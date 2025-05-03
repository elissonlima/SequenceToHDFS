package junit;

import me.elisson.reader.fasta.FASTAObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FASTAObjectTests {

    @DisplayName("Test Create Empty Object")
    @Test
    public void testCreateEmptyObject() {
        FASTAObject obj = new FASTAObject();
        assertEquals("", obj.getId());
        assertEquals("", obj.getSequence());
        assertEquals(0L, obj.getSize());
    }

    @DisplayName("Test Object Size Calculation")
    @Test
    public void testObjectSizeCalculation() {
        FASTAObject obj = new FASTAObject();
        obj.setId("NC_077216.1");//11
        obj.setSequence("ACCGGATGGCCGCGCGGG");//18
        assertEquals( 31L, obj.getSize());
    }

    @DisplayName("Test Object Size Calculation [After Append]")
    @Test
    public void testObjectSizeCalculationVariation() {
        FASTAObject obj = new FASTAObject();
        obj.setId("NC_077216.1");//11
        obj.setSequence("ACCGGATGGCCGCGCGGG");//18
        obj.appendSequence("ACC");
        assertEquals( 34L, obj.getSize());
    }

    @DisplayName("Test Copy")
    @Test
    public void testCopy() {
        FASTAObject obj = new FASTAObject();
        obj.setId("NC_077216.1");//11
        obj.setSequence("ACCGGATGGCCGCGCGGG");
        FASTAObject nObj = obj.copy();
        assertNotEquals(obj, nObj);
    }

    @DisplayName("Test toString() Method")
    @Test
    public void testToString() {
        FASTAObject obj = new FASTAObject();
        obj.setId(">NC_077680.1 |Tomato mottle leaf curl virus isolate BR:Jai13:08 segment DNA-A, complete sequence");
        obj.setSequence("ACCGGATGGCCGCGCGGGTTTTTTTGACCCGCTCCGTGATGTATTTTTTGTCTTTTACTA" +
                "TGTGGTCCAGTCAATAAATGACAAATATGACCGTCCAATCAGAAATGGTCCTCAAAGCCT" +
                "AATTATTTAAAAATACTTGGTCACTAAGTTTGGTAAAGTTTATAAATGATCCTTCCTCGT" +
                "AATGTTATACCAACTTTAAGTCATAATGCCTAAGCGTGATGCCCCATGGCGCTCAATGGC" +
                "GGGGACCTCAAAGGTTAGTCGGGCCGCCAATTTCTCCCCTCGTGGAGGAATCGGGTCGAA" +
                "A");
        String expected = ">NC_077680.1 |Tomato mottle leaf curl virus isolate BR:Jai13:08 segment DNA-A, complete sequence\n" +
                "ACCGGATGGCCGCGCGGGTTTTTTTGACCCGCTCCGTGATGTATTTTTTGTCTTTTACTA" +
                "TGTGGTCCAGTCAATAAATGACAAATATGACCGTCCAATCAGAAATGGTCCTCAAAGCCT" +
                "AATTATTTAAAAATACTTGGTCACTAAGTTTGGTAAAGTTTATAAATGATCCTTCCTCGT" +
                "AATGTTATACCAACTTTAAGTCATAATGCCTAAGCGTGATGCCCCATGGCGCTCAATGGC" +
                "GGGGACCTCAAAGGTTAGTCGGGCCGCCAATTTCTCCCCTCGTGGAGGAATCGGGTCGAA" +
                "A\n";
        assertEquals(expected, obj.toString());
    }

}
