package com.test.bytebuddy.agent.time;

public class AgentInterceptorTimed {

    public void hello() {
        System.out.println("hello world");
    }

    public void helloSleep() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("hello world helloSleep");
    }
}
