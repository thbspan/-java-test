package com.test.oom;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class RuntimeConstantPoolOOMTest {

    /**
     * <= JDK 6 -XX:PermSize=6M -XX:MaxPermSize=6M; -Xmx=6M
     *
     */
    @Test
    public void test() {
        Set<String> set = new HashSet<>();
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
