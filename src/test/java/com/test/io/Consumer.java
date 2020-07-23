package com.test.io;

import java.io.IOException;
import java.io.PipedReader;

public class Consumer extends Thread {
    private final PipedReader reader;

    public Consumer(PipedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        char[] buffer = new char[40];
        try {
            int length = reader.read(buffer);
            System.out.println("piped data:" + new String(buffer, 0, length));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
