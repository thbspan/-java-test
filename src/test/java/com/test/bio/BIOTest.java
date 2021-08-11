package com.test.bio;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BIOTest {

    private File file;

    @BeforeEach
    public void initFile() throws IOException {
        file = new File("out/text.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    @Test
    public void testFileIO() throws Exception {
        try (FileWriter writer = new FileWriter(file)) {
            // 写入操作系统的缓存中，每次写都会触发一次系统调用
            writer.write("Hello ");
            writer.write("IO");
            writer.write("\nwelcome!");
            // Thread.sleep(2222222222222L);
            // 写入磁盘，把系统缓存中的数据刷出去
            writer.flush();
            writer.close();
            Thread.sleep(2222222222222L);
        }
    }

    @Test
    public void testMemIO() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024)) {
            outputStream.writeBytes("hello world".getBytes(StandardCharsets.UTF_8));
            System.out.println(outputStream);

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray())) {
                byte[] data = new byte[1024];
                int read = inputStream.read(data);
                System.out.println(new String(data, 0, read, StandardCharsets.UTF_8));
            }
        }
    }

    @Test
    public void testBufferedIO() throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write("hello java\nhello world".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }

    @Test
    public void randomIO() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.write("hello random io\nhello world!".getBytes(StandardCharsets.UTF_8));
        // Thread.sleep(2222222222222L);
        FileChannel channel = randomAccessFile.getChannel();
        // mmap 内核系统调用|减少一次拷贝的过程
        // 堆外空间开启一块空间可以和内核直接访问
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 2048);
        mappedByteBuffer.put("hello jack".getBytes(StandardCharsets.UTF_8));
        // Thread.sleep(2222222222222L);
    }
}
