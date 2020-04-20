package com.test.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileRegionExample {

    public static void copyFile(String srcFile, String destFile) throws IOException {
        try (FileInputStream in = new FileInputStream(srcFile);
             FileOutputStream out = new FileOutputStream(destFile)
        ) {
            byte[] temp = new byte[1024];
            int length;
            while ((length = in.read(temp)) != -1) {
                out.write(temp, 0, length);
            }
        }
    }

    /**
     * 使用 Java NIO的FileChannel实现零拷贝
     */
    public static void copyFileWithFileChannel(String srcFileName, String destFileName) throws IOException {

        try (RandomAccessFile srcFile = new RandomAccessFile(srcFileName, "r");
             RandomAccessFile destFile = new RandomAccessFile(destFileName, "rw")) {
            FileChannel srcFileChannel = srcFile.getChannel();

            FileChannel destFileChannel = destFile.getChannel();

            srcFileChannel.transferTo(0, srcFileChannel.size(), destFileChannel);
        }
    }
}
