package com.test.clazz;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class ClassLayoutTest {

    /**
     * 可以关闭压缩指针后再测试 (-XX:-UseCompressedOops)
     */
    @Test
    public void testClassLayout() {
        ClassLayoutTest classLayoutTest = new ClassLayoutTest();
        System.out.println(ClassLayout.parseInstance(classLayoutTest).toPrintable());

        synchronized (classLayoutTest) {
            // 打印锁状态 f8 e3 9f 27 (11111000，最后两位是00，代表轻量级锁
            System.out.println(ClassLayout.parseInstance(classLayoutTest).toPrintable());
        }
    }

    /**
     * 打开偏向锁(默认关闭了) -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 偏向锁生效延迟时间
     */
    @Test
    public void testUseBiasedLocking() {
        ClassLayoutTest classLayoutTest = new ClassLayoutTest();

        synchronized (classLayoutTest) {
            System.out.println(Thread.currentThread().getId());
            // 如果计算了hashcode、输出 6a af 24 40 (01101010，最后三位是 010重量级锁，无法使用偏向锁
            classLayoutTest.hashCode();
            // 打印锁状态 05 b0 56 e0 (00000101，最后三位是101，代表偏向锁
            System.out.println(ClassLayout.parseInstance(classLayoutTest).toPrintable());
        }
    }
}
