package com.test.defaultmethod;

public interface INet {
    void network();

    default void connection() {
        System.out.println("INet connection");
    }
}