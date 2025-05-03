package me.elisson.reader.fasta;

import me.elisson.reader.base.BaseReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FASTAReader extends BaseReader {

    private final String inputFilePath;
    private final FileReader fr;
    private final BufferedReader br;
    private String currentLine;
    private FASTAObject currentObj;
    private boolean hasLines;
    private boolean readLast;


    public FASTAReader(String inputFilePath) throws FileNotFoundException {
        this.inputFilePath = inputFilePath;
        this.fr = new FileReader(this.inputFilePath);
        this.br = new BufferedReader(this.fr);
        this.currentLine = "";
        this.currentObj = new FASTAObject();
        this.hasLines = true;
        this.readLast = false;

        this.getNextSequence(); //This will be an empty Sequence
        if (hasLines)
            this.getNextSequence();
    }

    private void getNextSequence() {

        currentObj = new FASTAObject();
        currentObj.setId(currentLine);

        while (true) {
            try {
                if ((currentLine = this.br.readLine()) == null) {
                    hasLines = false;
                    readLast = false;
                    break;
                }
            } catch (IOException ex) {
                hasLines = false;
                readLast = false;
                break;
            }

            if (currentLine.startsWith(">")) {
                readLast = false;
                break;
            } else {
                currentObj.appendSequence(currentLine);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return hasLines || !readLast;
    }

    @Override
    public FASTAObject next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        FASTAObject res = this.currentObj.copy();
        readLast = true;

        if (hasLines) {
            getNextSequence();
        }
        return res;
    }
}
