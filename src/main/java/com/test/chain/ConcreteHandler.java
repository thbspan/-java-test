package com.test.chain;

public class ConcreteHandler extends Handler {
    @Override
    public void handleRequest(Object param) {
        if (getSuccessor() != null) {
            System.out.println("放过请求");
            getSuccessor().handleRequest(param);
        } else {
            System.out.println("处理请求");
        }
    }
}
