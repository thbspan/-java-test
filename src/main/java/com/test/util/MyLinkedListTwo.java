package com.test.util;

public class MyLinkedListTwo {
    private final Node head;
    private int size;

    public MyLinkedListTwo() {
        head = new Node(-1);
        head.next = head;
        head.prev = head;
    }

    public void addAtHead(int val) {
        Node next = head.next;
        Node node = new Node(val, head, next);
        next.prev = node;
        head.next = node;
        ++size;
    }

    public void addAtTail(int val) {
        Node prev = head.prev;
        Node node = new Node(val, prev, head);
        prev.next = node;
        head.prev = node;
        ++size;
    }

    public void addAtIndex(int index, int val) {
        if (index <= size) {
            Node prev = head;
            int i;
            for (i = 0; i < index && prev.next != head; i++) {
                prev = prev.next;
            }
            if (i == index) {
                ++size;
                Node next = prev.next;
                Node node = new Node(val, prev, next);
                prev.next = node;
                next.prev = node;
            }
        }
    }

    public int get(int index) {
        if (index < size) {
            int i;
            Node current = head;
            for (i = 0; i <= index && current.next != head; i++) {
                current = current.next;
            }
            if (i == index + 1) {
                return current.val;
            }
        }
        return -1;
    }

    public void deleteAtIndex(int index) {
        if (index < size) {
            int i;
            Node current = head;
            for (i = 0; i <= index && current.next != head; i++) {
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            --size;
            current.prev = null;
            current.next = null;
        }
    }

    static class Node {
        int val;
        Node prev;
        Node next;

        Node(int val) {
            this.val = val;
        }

        Node(int val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }
}
