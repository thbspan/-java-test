package com.test.util;

import java.util.HashMap;
import java.util.Map;

public class Base128 {
    /**
     * 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\xb5\xb6\xb7\xbc\xbd\xbe\xc1\xc2\xc3\xc4\xc5\xc6\xc7\xc8\xc9\xca\xcb\xcc\xcd\xce\xcf\xd1\xd2\xd3\xd4\xd5\xd6\xd7\xd8\xd9\xda\xdb\xdc\xdd\xde\xdf\xe1\xe2\xe3\xe4\xe5\xe6\xe7\xe8\xe9\xea\xeb\xec\xed\xee\xef\xf1\xf2\xf3\xf4\xf5\xf6\xf7\xf8\xf9\xfa\xfb\xfc\xfd\xfe\xff
     */
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
