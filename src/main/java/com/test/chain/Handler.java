package com.test.chain;

/**
 * 责任链模式
 */
public abstract class Handler {

    /**
     * 持有后续的责任对象
     */
    protected Handler successor;

    /**
     * 示例请求方法
     */
    public abstract void handleRequest(Object param);

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
}
