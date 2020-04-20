package com.test.jvm;

public class StringHeapError {

    /**
     * 设置jvm参数
     * -Xms10M -Xmx10M -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
     */
    public static void main(String[] args) {
        String temp = "world";
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String str = temp + temp;
            temp = str;
            str.intern();
        }
    }
}
