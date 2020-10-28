package com.test.iterator;

public class NameRepository implements Container {
    private final String[] names = {"Robert", "John", "Julie", "Lora"};

    @Override
    public Iterator getIterator() {
        return new StringArrayIterator(names);
    }
}
