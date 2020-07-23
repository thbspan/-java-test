package com.test.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class SequenceInputStreamTest {

    /**
     * 组合多个InputStream
     */
    @Test
    public void test() throws IOException {
        InputStream input1 = new ByteArrayInputStream("Hello ".getBytes(StandardCharsets.UTF_8));
        InputStream input2 = new ByteArrayInputStream("Peter!".getBytes(StandardCharsets.UTF_8));

        // sequenceInputStream 关闭时，它还将关闭它从中读取的 InputStream 实例
        try (SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(Arrays.asList(input1, input2)))) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[100];
            int length;
            while ((length = sequenceInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            System.out.println(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
        }
    }
}
