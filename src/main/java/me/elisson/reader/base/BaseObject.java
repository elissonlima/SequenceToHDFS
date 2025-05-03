package me.elisson.reader.base;

import me.elisson.reader.fasta.FASTAObject;

public abstract class BaseObject {

    public abstract String getId();
    public abstract void setId(String id);
    public abstract String getSequence();
    public abstract void setSequence(String sequence);
    public abstract void appendSequence(String sequence);
    public abstract Long getSize();
    public abstract BaseObject copy();
    public abstract String toString();

}
