package me.elisson.reader.base;


import java.util.Iterator;

public abstract class BaseReader implements Iterator<BaseObject> {

    public abstract boolean hasNext();
    public abstract BaseObject next();

}
