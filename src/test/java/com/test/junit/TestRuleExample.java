package com.test.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestRuleExample implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.err.println(description.getTestClass().getName() + '.' + description.getDisplayName());
                base.evaluate();
            }
        };
    }
}
