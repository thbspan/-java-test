package com.test.clazz;

public class Child extends Parent{
    static {
        System.out.println("child static block code");
    }
    {
        System.out.println("child instance block code");
    }

    public Child() {
        System.out.println("child constructor code");
    }
}
