package com.test.bytebuddy.agent;

import org.junit.jupiter.api.Test;

public class AgentToStringTest {

    @Test
    public void testAgentAnnotation() {

    }

    /**
     * 运行时添加 -javaagent:E:\\intellij-workspace\\java-test\\build\\libs\\java-test-1.0-SNAPSHOT.jar
     */
    public static void main(String[] args) {
        AgentBean agentBean = new AgentBean();
        AgentBeanWithAnnotation agentBeanWithAnnotation = new AgentBeanWithAnnotation();
        System.out.println(agentBean);
        System.out.println(agentBeanWithAnnotation);
    }
}
