package com.test.concurrent.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Test;

public class ReentrantReadWriteLockTest {

    /**
     * 测试在不同线程中先获取写锁、再获取读锁
     * get write lock
     * write lock release
     * get read lock
     * read lock release
     */
    @Test
    public void testWriteRead() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        new Thread(() -> {
            try {
                // ensure write lock get first
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.readLock().lock();
            System.out.println("get read lock");
            lock.readLock().unlock();
            System.out.println("read lock release");
        }).start();
        lock.writeLock().lock();
        try {
            System.out.println("get write lock");
            Thread.sleep(5000);
        } finally {
            lock.writeLock().unlock();
            System.out.println("write lock release");
        }

    }
    /**
     * 测试在相同线程中先获取写锁、再获取读锁
     * get write lock
     * get read lock
     * read lock release
     * write lock release
     */
    @Test
    public void testWriteReadInThread() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        System.out.println("get write lock");
        lock.readLock().lock();
        System.out.println("get read lock");
        lock.readLock().unlock();
        System.out.println("read lock release");
        lock.writeLock().unlock();
        System.out.println("write lock release");

    }

    /**
     * 测试在相同线程中先获取写锁、再获取读锁
     * <br/>
     * 发生死锁，需要先释放读锁
     */
    @Test
    public void testReadWriteInThread() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        System.out.println("get read lock");
        lock.writeLock().lock();
        System.out.println("get write lock");
        lock.writeLock().unlock();
        System.out.println("write lock release");
        lock.readLock().unlock();
        System.out.println("read lock release");
    }
}
