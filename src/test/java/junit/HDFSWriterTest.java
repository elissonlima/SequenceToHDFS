package junit;

import me.elisson.hdfs.HDFSWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HDFSWriterTest {

    @Test
    public void TestBlockWriter() {
        //HDFSWriter writer = HDFSWriter()
        try {
            Path tempDir = Files.createTempDirectory("test_hdfs");

            Configuration conf = new Configuration();
            conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, tempDir.toAbsolutePath().toString());

            MiniDFSCluster.Builder builder = new MiniDFSCluster.Builder(conf);
            try (MiniDFSCluster hdfsCluster = builder.build()) {
                String basePath = "hdfs://localhost:" + hdfsCluster.getNameNodePort();
                String targetPath = basePath + "/sequence.fasta";

                HDFSWriter writer = new HDFSWriter(targetPath);
                String sequence = ">NC_083851.1 |Bird's-foot trefoil nucleorhabdovirus isolate LC, complete genome\n" +
                        "CACAATATACATGATGCTCGAACTGTTGGCCTATAAGCCTGCAAACCGCTCGGACTGTCG" +
                        "TTTTTTGGAACTCTGATTATATAAGAAAAACCTTTTAATCACGAGGGAGATCCGAGGGG" +
                        "CGTGGTGGCTCCAGGGGACACGGGGAGTCAGAGGATTGTCGGAAACCTTCAGCACCCTAA" +
                        "CCGTTTATCCCTCTGGCCAAGAGGGATCATAGTTTATTGACAAGAGTGGATAAATCCACA\n" +
                        "TCAAGC";
                writer.writeBlock(sequence);

            } catch(IOException ex) {
                System.err.println("Could not build the mini hdfs for tests: " + ex.toString());
            }
        } catch (IOException ex) {
            System.err.println("Could not create the mini hdfs for tests: " + ex.toString());
        }
    }
}
