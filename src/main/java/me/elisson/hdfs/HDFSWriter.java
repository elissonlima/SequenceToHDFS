package me.elisson.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

public class HDFSWriter {

    private Configuration conf;
    private FSDataOutputStream outStream;

    public HDFSWriter(String destinationPath) throws IOException {
        this.conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(destinationPath), conf);
        this.outStream = fs.create(new Path(destinationPath));
    }

    public void writeBlock(String content) throws IOException {
        this.outStream.write(content.getBytes("UTF-8"));
        this.outStream.hflush();
    }

}
