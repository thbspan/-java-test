package com.test.bytebuddy.agent;

import java.util.StringJoiner;

public class AgentBean {
    private String name = "byte-buddy";
    private Integer age = 20;

    @Override
    public String toString() {
        return new StringJoiner(", ", AgentBean.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }
}
