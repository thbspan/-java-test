package com.test.junit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

public class ExtraVerifyRuleTest {
    private String sequence = "";

    @Rule
    public Verifier verifier = new Verifier() {
        @Override
        protected void verify() throws Throwable {
            Assert.assertEquals(sequence, "test verify");
        }
    };

    @Test
    public void example() {
        sequence += "test";

        Assert.assertEquals(sequence, "test");
    }

    @Test
    public void verify() {
        sequence = "test verify";
    }
}
