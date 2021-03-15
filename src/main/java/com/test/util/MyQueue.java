package com.test.util;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 使用两个栈实现先进先出队列
 */
public class MyQueue<T> {
    private final Deque<T> inStack;
    private final Deque<T> outStack;

    public MyQueue() {
        this.inStack = new LinkedList<>();
        this.outStack = new LinkedList<>();
    }

    public void push(T value) {
        inStack.push(value);
    }

    public T pop() {
        if (outStack.isEmpty()) {
            in2out();
        }
        return outStack.pop();
    }

    public T peek() {
        if (outStack.isEmpty()) {
            in2out();
        }
        return outStack.peek();
    }

    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    private void in2out() {
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
    }
}
