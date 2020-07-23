package com.test.io;

import java.io.PipedWriter;
import java.util.concurrent.TimeUnit;

public class Producer extends Thread {
    private final PipedWriter writer;

    public Producer(PipedWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            writer.write("Hello World!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
