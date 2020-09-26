package com.test.io;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import org.junit.jupiter.api.Test;

public class PipedReaderWriterTest {

    @Test
    public void test() throws IOException, InterruptedException {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        Producer producer = new Producer(writer);
        Consumer consumer = new Consumer(reader);

        writer.connect(reader);
        consumer.start();
        producer.start();

        consumer.join();
        producer.join();
    }
}
