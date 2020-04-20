package com.test.junit;

import org.junit.Ignore;
import org.junit.Test;

public class IgnoreTest {

    @Ignore
    @Test
    public void testIgnore() {
        String name = null;
        System.out.println(name.toString());
    }


}
