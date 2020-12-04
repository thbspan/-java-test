package com.test.util;

public class MyLinkedList {
    private int size;
    private final ListNode head;
    private final ListNode tail;

    public MyLinkedList() {
        tail = new ListNode(-1);
        head = new ListNode(-1, tail);
    }

    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    public void addAtIndex(int index, int val) {
        if (index <= size) {
            ListNode prev = head;
            int i;
            // 找到前一个元素
            for (i = 0; i < index && prev != tail; i++) {
                prev = prev.next;
            }

            size++;
            prev.next = new ListNode(val, prev.next);
        }
    }

    public int get(int index) {
        if (index < size) {
            int i;
            ListNode current = head;
            for (i = -1; i < index && current != tail; i++) {
                current = current.next;
            }
            if (i == index) {
                return current.val;
            }
        }
        return -1;
    }

    public void deleteAtIndex(int index) {
        if (index < size) {
            ListNode prev = head;
            int i;
            // 找到前一个元素
            for (i = 0; i < index && prev != tail; i++) {
                prev = prev.next;
            }
            ListNode deleteNode = prev.next;
            prev.next = deleteNode.next;
            size--;
            // help gc
            deleteNode.next = null;
        }
    }

    static class ListNode {
        private final int val;
        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}

