package com.test.zk;

import org.I0Itec.zkclient.ZkClient;

public class ZKClientExample {
    public static void main(String[] args) {

        ZkClient zkClient = new ZkClient("localhost:2181", 30_000, 30_000);
        zkClient.createPersistent("/demo/test", true);
        System.out.println(zkClient);
        for (String child : zkClient.getChildren("/demo")) {
            System.out.println(child);
        }
    }
}
