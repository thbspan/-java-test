package com.test.util;

import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest {

    @Test
    public void testToken() {
        StringTokenizer st = new StringTokenizer("622666****1234"," *", false);
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }

    @Test
    public void testTokenWithSpace() {
        StringTokenizer st = new StringTokenizer("Hello World");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
}
