package com.test.junit;

import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TimeOutTest {

    /**
     * 超时时间，作用域为整个类的测试实例
     */
    @Rule
    public Timeout allTimeout = Timeout.seconds(3);

    @Test
    public void testTimeOut() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
