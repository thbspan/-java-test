package com.test.bytebuddy.agent.time;

public class TimingInterceptorTest {

    /**
     * 运行时添加 -javaagent:E:\\intellij-workspace\\java-test\\build\\libs\\java-test-1.0-SNAPSHOT.jar
     */
    public static void main(String[] args) throws InterruptedException {
        AgentInterceptorTimed agentInterceptorTimed = new AgentInterceptorTimed();
        agentInterceptorTimed.hello();
        agentInterceptorTimed.helloSleep();
    }
}
