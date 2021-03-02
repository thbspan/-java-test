package com.test.autoclose;

import org.junit.jupiter.api.Test;

public class AutoCloseableTest {

    @Test
    public void test() throws Exception {
        try (ExampleResource resource = new ExampleResource(1);
             ExampleResource resource2 = new ExampleResource(2)) {
            resource.read();
            resource2.read();
        }

        {
            // 模拟对象销毁
            ExampleResource resource3 = new ExampleResource(3);
            try {
                resource3.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.gc();
        Thread.sleep(10_000);
    }
}
