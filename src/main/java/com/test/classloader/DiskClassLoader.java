package com.test.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DiskClassLoader extends ClassLoader {
    private final String libPath;

    public DiskClassLoader(String path) {
        libPath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(libPath, fileName);

        try {
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                int len;
                while ((len = is.read()) != -1) {
                    bos.write(len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte [] data = bos.toByteArray();
            bos.close();
            is.close();

            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        } else {
            return name;
        }
    }
}
