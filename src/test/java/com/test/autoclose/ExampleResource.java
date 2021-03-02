package com.test.autoclose;

public class ExampleResource implements AutoCloseable {
    private final int id;

    public ExampleResource(int id) {
        this.id = id;
    }

    @Override
    public void close() throws Exception {
        System.out.println("----resource closed----" + id);
    }

    public void read() {
        System.out.println("read");
        // throw new RuntimeException("read error");
    }
}
