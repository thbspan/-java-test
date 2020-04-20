package com.test.factory;

public class IntelCpu implements Cpu {
    private int pins;

    public IntelCpu(int pins) {
        this.pins = pins;
    }

    @Override
    public void calculate() {
        System.out.println("Intel CPU的针脚数：" + pins);
    }
}
