package com.test.defaultmethod;

public interface IPhoto {
    void photo();

    default void connection() {
        System.out.println("IPhoto connection");
    }
}
