package com.test.util;

import java.util.HashMap;
import java.util.Map;

public class Base128 {
    private static final char[] SYMBOL_TABLE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzµ¶·¼½¾ÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÑÒÓÔÕÖ×ØÙÚÛÜÝÞßáâãäåæçèéêëìíîïñòóôõö÷øùúûüýþÿ".toCharArray();

    private static final Map<Character, Integer> INDEX_TABLE = new HashMap<>();

    static {
        for (int i = 0; i < SYMBOL_TABLE.length; i++) {
            INDEX_TABLE.put(SYMBOL_TABLE[i], i);
        }
    }

    public static String encode(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int tail = 0;
        for (int i = 0; i < data.length; i++) {
            int mov = (i % 7 + 1);
            int cur = 0XFF & data[i];
            // 向右移一位
            int code = tail + (cur >> mov);
            result.append(SYMBOL_TABLE[code]);
            tail = (0xFF & (cur << (8 - mov))) >> 1;
            if (mov == 7) {
                result.append(SYMBOL_TABLE[tail]);
                tail = 0;
            }
        }
        result.append(SYMBOL_TABLE[tail]);
        return result.toString();
    }

    public static byte[] decode(String base128) {
        if (base128 == null || base128.length() == 0) {
            return new byte[]{};
        }
        int length = (int) Math.floor(base128.length() * 0.875);
        byte[] result = new byte[length];
        int idx = 0;
        int head = INDEX_TABLE.get(base128.charAt(0)) << 1;
        for (int i = 1; i < base128.length(); ) {
            int mod = i % 8;
            int code = INDEX_TABLE.get(base128.charAt(i++));
            result[idx++] = (byte) (0xFF & (head + (code >> (7 - mod))));
            if (mod == 7) {
                head = 0xFF & (INDEX_TABLE.get(base128.charAt(i++)) << 1);
            } else {
                head = 0xFF & (code << (mod + 1));
            }
        }
        return result;
    }
}
