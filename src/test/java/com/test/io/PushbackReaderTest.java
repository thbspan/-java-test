package com.test.io;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import org.junit.Test;

public class PushbackReaderTest {

    @Test
    public void test() throws IOException {
        StringReader stringReader = new StringReader("0123456789");
        PushbackReader pushbackReader = new PushbackReader(stringReader, 100);

        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[100];
        int n = pushbackReader.read(buffer, 0, 3);
        builder.append(buffer, 0, 3);
        System.out.printf("In the first step, %d characters have been read\n", n);
        System.out.println(buffer);
        pushbackReader.unread(new char[]{'a', 'b', 'c'});
        pushbackReader.unread(new char[]{'d', 'e'});
        pushbackReader.unread(new char[]{'f', 'g', 'h', 'i'});
        int c;
        while ((c = pushbackReader.read(buffer)) != -1) {
            builder.append(buffer, 0, c);
        }
        System.out.printf("The final data: %s.\n", builder);
    }
}
