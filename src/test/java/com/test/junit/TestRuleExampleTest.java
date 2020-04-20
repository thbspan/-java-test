package com.test.junit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class TestRuleExampleTest {

    @Rule
    public TestRuleExample testRule = new TestRuleExample();

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
