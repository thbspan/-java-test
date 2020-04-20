package com.test.pool;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.pool2.ObjectPool;

public class PooledReaderUtil {

    private ObjectPool<StringBuffer> pool;

    public PooledReaderUtil(ObjectPool<StringBuffer> pool) {
        this.pool = pool;
    }

    public String readToString(Reader reader) throws IOException{
        StringBuffer buffer;
        try (Reader in = reader){
            buffer = pool.borrowObject();
            for (int c = in.read(); c != -1; c = in.read()) {
                buffer.append((char) c);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to borrow buffer from pool" + e.toString());
        }
    }

}
