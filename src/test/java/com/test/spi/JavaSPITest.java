package com.test.spi;

import java.util.ServiceLoader;

import org.junit.jupiter.api.Test;

public class JavaSPITest {

    @Test
    public void sayHello() {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI Test");
        serviceLoader.forEach(Robot::sayHello);
    }
}