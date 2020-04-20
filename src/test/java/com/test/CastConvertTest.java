package com.test;

import org.junit.Test;

public class CastConvertTest {

    @Test
    public void testConvertLongToInt() {
        long l = 1296316800000L;
        // 直接舍弃最左边的32位，所有可能原来的正数变成负数
        System.out.println((int) l);// -763323392
    }
}
