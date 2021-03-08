package com.test.hash;

/**
 * 模拟机器节点
 */
public class Node {
    private String ip;
    private String name;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node(String ip, String name) {

        this.ip = ip;
        this.name = name;
    }
}
