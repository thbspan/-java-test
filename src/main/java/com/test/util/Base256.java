package com.test.util;

import java.util.HashMap;
import java.util.Map;

public class Base256 {
    private static final char[] SYMBOL_TABLE = "àáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſƀƁƂƃƄƅƆƇƈƉ=_-`~|[]{}ƞ?,()^*$%!#.ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final Map<Character, Integer> INDEX_TABLE = new HashMap<>();

    static {
        for (int i = 0; i < SYMBOL_TABLE.length; i++) {
            INDEX_TABLE.put(SYMBOL_TABLE[i], i);
        }
    }

    public static String encode(byte[] data) {
        StringBuilder builder = new StringBuilder(data.length);
        for (byte b : data) {
            builder.append(byteToBase256(b));
        }
        return builder.toString();
    }

    public static byte[] decode(String base256) {
        byte[] output = new byte[base256.length()];
        for (int i = 0, size = base256.length(); i < size; i++) {
            char c = base256.charAt(i);
            output[i] = (byte) (INDEX_TABLE.get(c) - 128);
        }
        return output;
    }


    private static char byteToBase256(byte b) {
        // byte = [-128, 127)
        return SYMBOL_TABLE[b + 128];
    }
}
