package com.test.iterator;

public class StringArrayIterator implements Iterator {
    private final String[] args;
    private int index;

    public StringArrayIterator(String[] args) {
        this.args = args;
    }

    @Override
    public boolean hasNext() {
        return index < args.length;
    }

    @Override
    public Object next() {
        if (index < args.length) {
            return args[index++];
        }
        return null;
    }
}
