package com.test.junit;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class ExternalResource2Test {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFolder() throws IOException {
        File file = temporaryFolder.newFile("output.txt");
        System.out.println(file.getAbsoluteFile());
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write("test");
        }
    }
}
