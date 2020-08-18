package com.test.clazz;

public class Parent {
    static {
        System.out.println("parent static block code");
    }
    {
        System.out.println("parent instance block code");
    }

    public Parent() {
        System.out.println("parent constructor code");
    }
}
