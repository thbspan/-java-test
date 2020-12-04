package com.test.util;

import org.junit.jupiter.api.Test;

public class MyLinkedListTest {

    @Test
    public void test() {
        MyLinkedListTwo linkedList = new MyLinkedListTwo();
        linkedList.addAtHead(7);
        linkedList.addAtHead(2);
        linkedList.addAtHead(1);
        linkedList.addAtIndex(3, 0);
        linkedList.deleteAtIndex(2);
        linkedList.addAtHead(6);
        linkedList.addAtTail(4);
        System.out.println(linkedList.get(4));
        linkedList.addAtHead(4);
        linkedList.addAtIndex(5, 0);
        linkedList.addAtHead(6);
        System.out.println(linkedList.get(0));
    }
}
