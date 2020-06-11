package com.test.util;

import java.util.StringTokenizer;

import org.junit.Test;

public class StringTokenizerTest {

    @Test
    public void testToken() {
        StringTokenizer st = new StringTokenizer("622666****1234"," *", false);
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
}
