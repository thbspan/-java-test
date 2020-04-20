package com.test.factory;

public class AmdCpu implements Cpu {
    private int pins;

    public AmdCpu(int pins) {
        this.pins = pins;
    }

    @Override
    public void calculate() {
        System.out.println("AMD CPU的针脚数：" + pins);
    }
}
