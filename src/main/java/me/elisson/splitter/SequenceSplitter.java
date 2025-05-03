package me.elisson.splitter;

import me.elisson.reader.base.BaseObject;
import me.elisson.reader.base.BaseReader;

import java.util.Iterator;

public class SequenceSplitter implements Iterator<String> {

    private final Long splitSize;
    private final BaseReader reader;
    private StringBuilder buffer;
    private long currentSize;

    private BaseObject lastObject;
    private boolean hasLast;

    public SequenceSplitter(BaseReader reader) {
        this.reader = reader;
        this.splitSize = 128 * 1024 * 1024L; //Default Hadoop Block Size
        this.buffer = new StringBuilder();
        this.currentSize = 0L;
        this.hasLast = false;

        buildNext();
    }

    public SequenceSplitter(BaseReader reader, Long splitSize) {
        this.reader = reader;
        this.splitSize = splitSize;
        this.buffer = new StringBuilder();
        this.currentSize = 0L;
        this.hasLast = false;

        buildNext();
    }

    private void buildNext() {

        if (hasLast) {
            this.buffer.append(lastObject);
            this.currentSize += lastObject.getSize();
            hasLast = false;
        }

        while (reader.hasNext()) {
            BaseObject obj = reader.next();

            if ((this.currentSize + obj.getSize()) > splitSize) {
                //System.out.println("Current Size: " + this.currentSize + " + Current Object Size: " + obj.getSize()
                //        + " Greater than the split size: " + this.splitSize);
                this.lastObject = obj.copy();
                this.hasLast = true;
                break;
            } else {
                this.buffer.append(obj);
                this.currentSize += obj.getSize();
            }
        }
    }

    public boolean hasNext() {
        return reader.hasNext() || !buffer.toString().isEmpty();
    }

    public String next() {
        String res = this.buffer.toString();
        buffer = new StringBuilder();
        this.currentSize = 0L;
        buildNext();
        return res;
    }
}
