package com.test.util;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base128Test {

    @Test
    public void testEncode() {
        String str = "一个事务由多个步骤组成，并且这些步骤的参与者位于不同的服务器、应用系统中";
        System.out.println(Base128.encode(str.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testDecode() {
        String base128 = "òkGEbáÓãÛYüÖÓ7ÌKÖùqÆÕIñgóhqÜÊgÆæÛßSçÁ¾ÝyÃvtRtIñKôlóUbétåÔèzQÓJÌQ½vnøNIñEôWGÜböRãÛZÚÈÁtÈGÃvóedQvDòáÒUiåÎâ·WyÙÒÑÌKÒvöxÝU÷VòkLÍ";
        System.out.println(new String(Base128.decode(base128), StandardCharsets.UTF_8));
    }
}
