package com.test.lock;

public class FinalExample {
    int i; // 普通变量
    final int j; // final变量
    static FinalExample obj;

    public FinalExample() {
        i = 1; // 写普通域
        j = 2; // 写final变量
    }

    public static void writer() { // 写线程A执行
        obj = new FinalExample();
    }

    public static void reader() { // 读线程B执行
        FinalExample object = obj; // 读对象引用
        int a = object.i; // 读普通域
        int b = object.j; // 读 final 域
    }
}
