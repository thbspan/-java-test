package com.test.chain;

import org.junit.jupiter.api.Test;

public class ChainTest {

    @Test
    public void test() {
        Handler handler1 = new ConcreteHandler();
        Handler handler2 = new ConcreteHandler();
        handler1.setSuccessor(handler2);
        //提交请求
        handler1.handleRequest("test");
    }
}
