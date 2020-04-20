package com.test.abstractfactory;

import com.test.factory.Cpu;
import com.test.factory.Mainboard;

public interface AbstractFactory {
    Cpu createCpu();

    Mainboard createMainboard();
}
