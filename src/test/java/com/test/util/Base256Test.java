package com.test.util;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base256Test {

    @Test
    public void testEncode() {
        String str = "一个事务由多个步骤组成，并且这些步骤的参与者位于不同的服务器、应用系统中";
        System.out.println(Base256.encode(str.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testDecode() {
        String base256 = "ńĘàńĘĊńĚëŅêāŇôđŅĄúńĘĊņčąŉĊĄŇěäņèðŏĜìŅęĖńĘôňğùńĚûņčąŉĊĄŇúäŅïâńĘîňàåńĝíńĚîńĘíŅðìŇúäņüíŅêāŅùĈŃàáŅĚôŇôĈŇēěŇěÿńĘč";
        System.out.println(new String(Base256.decode(base256), StandardCharsets.UTF_8));
    }
}
