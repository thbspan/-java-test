package com.test.file;

import java.io.IOException;

import org.junit.Test;

public class FileRegionExampleTest {

    @Test
    public void testCopyFileWithFileChannel() throws IOException {
        FileRegionExample.copyFileWithFileChannel("G:\\BaiduNetdiskDownload\\example.mp4", "G:\\BaiduNetdiskDownload\\test.mp4");
    }
}
