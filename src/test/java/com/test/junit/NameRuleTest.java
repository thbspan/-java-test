package com.test.junit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class NameRuleTest {
    @Rule
    public TestName name = new TestName();

    @Test
    public void testA() {
        Assert.assertEquals("testA", name.getMethodName());
    }

    @Test
    public void testB() {
        Assert.assertEquals("testB", name.getMethodName());
    }
}
