package com.test.defaultmethod;

public class SmartWatch implements IPhoto, INet {
    @Override
    public void network() {
        System.out.println("SmartWatch network");
    }

    @Override
    public void photo() {
        System.out.println("SmartWatch photo");
    }

    /**
     * 必须重写：多个接口中包含相同的默认方法
     */
    @Override
    public void connection() {
        System.out.println("SmartWatch connection");
    }
}
