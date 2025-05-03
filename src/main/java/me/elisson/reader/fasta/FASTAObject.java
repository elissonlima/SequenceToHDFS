package me.elisson.reader.fasta;

import me.elisson.reader.base.BaseObject;

import java.nio.charset.StandardCharsets;

public class FASTAObject extends BaseObject {

    private String id;
    private String sequence;

    public FASTAObject() {
        this.id = "";
        this.sequence = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void appendSequence(String sequence) {
        this.sequence = this.sequence + sequence;
    }

    public Long getSize() {
        byte[] idBytes = id.getBytes(StandardCharsets.UTF_8);
        byte[] sequenceBytes = sequence.getBytes(StandardCharsets.UTF_8);

        long res = idBytes.length + sequenceBytes.length;

        if (res > 0) {
            // Added the two bytes for the line breaks added
            // to the end of the ID and to end of the sequence
            // String when parsing the object to String
            res += 2;
        }

        return res;
    }

    public FASTAObject copy() {
        FASTAObject nObj = new FASTAObject();
        nObj.setId(this.id);
        nObj.setSequence(this.sequence);

        return nObj;
    }

    public String toString() {
        return this.id + '\n' + this.sequence + '\n';
    }
}
