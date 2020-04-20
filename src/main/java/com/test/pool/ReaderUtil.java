package com.test.pool;

import java.io.IOException;
import java.io.Reader;

public class ReaderUtil {

    public String readToString(Reader reader) throws IOException {
        StringBuilder buf = new StringBuilder();
        try (Reader in = reader) {
            for (int c = in.read(); c != -1; c = in.read()) {
                buf.append((char) c);
            }
            return buf.toString();
        } catch (IOException e) {
            throw e;
        }
    }
}
