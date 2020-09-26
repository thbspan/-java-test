package com.test.pool;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.Test;

public class PooledReaderUtilTest {

    @Test
    public void test() throws IOException {
        PooledReaderUtil readerUtil = new PooledReaderUtil(new GenericObjectPool<>(new StringBufferFactory()));
        System.out.println(readerUtil.readToString(new StringReader("hello")));
    }
}
