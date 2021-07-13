package com.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 跳跃表
 */
public class SkipList<E, T extends Comparable<? super T>> {
    public static final int MAX_LEVEL = 1 << 6;
    public static final int DEFAULT_LEVEL = 4;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final int level;
    private final SkipListNode<E, T> head;

    public SkipList() {
        this(DEFAULT_LEVEL);
    }

    /**
     * 跳跃表初始化
     */
    public SkipList(int level) {
        this.level = level;
        int i = level;
        SkipListNode<E, T> prev = null;
        // 从底部节点开始创建链表
        while (i-- != 0) {
            Comparable<T> comparable = (c) -> -1;
            SkipListNode<E, T> temp = new SkipListNode<>(null, (T) comparable);
            temp.down = prev;
            prev = temp;
        }
        head = prev;
    }

    public void put(E val, T score) {
        SkipListNode<E, T> t = head;
        List<SkipListNode<E, T>> paths = new ArrayList<>();
        // 1、找到需要插入的位置
        while (t != null) {
            // 表示存在该值的点，表示需要更新该节点
            if (t.score.compareTo(score) == 0) {
                SkipListNode.Entity<E> e = t.entity;
                // 插入到链表的尾部
                e.next = new SkipListNode.Entity<>(val, null);
                return;
            }
            // 查找插入的位置
            if (t.next == null || t.next.score.compareTo(score) > 0) {
                paths.add(t);
                t = t.down;
            } else {
                t = t.next;
            }
        }

        // 随机生成新节点的层级
        int lev = randomLevel();
        // 从后向前遍历path中的每一个节点，再其后添加一个新的节点
        SkipListNode<E, T> down = null, newNode, path;
        SkipListNode.Entity<E> e = new SkipListNode.Entity<>(val, null);
        for (int i = level - 1; i >= level - lev; i--) {
            // 创建新的节点
            newNode = new SkipListNode<>(e, score);
            path = paths.get(i);
            newNode.next = path.next;
            path.next = newNode;
            newNode.down = down;
            down = newNode;
        }
    }

    public List<E> get(T score) {
        SkipListNode<E, T> t = head;
        while (t != null) {
            if (t.score.compareTo(score) == 0) {
                return t.entity.toList();
            }

            if (t.next == null || t.next.score.compareTo(score) > 0) {
                t = t.down;
            } else {
                t = t.next;
            }
        }
        return null;
    }

    public List<E> find(final T begin, final T end) {
        SkipListNode<E, T> t = head;
        SkipListNode<E, T> beginNode = null;
        while (t != null) {
            beginNode = t;
            if (t.next == null || t.next.score.compareTo(begin) >= 0) {
                t = t.down;
            } else {
                t = t.next;
            }
        }
        //循环链表
        List<E> list = new ArrayList<>();
        if (beginNode != null) {
            for (SkipListNode<E, T> n = beginNode.next; n != null; n = n.next) {
                list.addAll(n.entity.toList());
            }
        }
        return list;
    }

    /**
     * 根据score值来删除节点
     */
    public void delete(T score) {
        // 找到前驱节点
        SkipListNode<E, T> t = head;
        while (t != null) {
            if (t.next == null || t.next.score.compareTo(score) > 0) {
                // 向下查找
                t = t.down;
            } else if (t.next.score.compareTo(score) == 0) {
                // 找到了需要删除的节点
                t.next = t.next.next;
                t = t.down;
            } else {
                // 向右查找
                t = t.next;
            }
        }

    }

    private int randomLevel() {
        int lev = 1;
        while (RANDOM.nextInt() % 2 == 0 && lev < this.level) {
            lev++;
        }
        return lev;
    }

    static class SkipListNode<E, T extends Comparable<? super T>> {
        Entity<E> entity;
        T score;
        private SkipListNode<E, T> next;
        private SkipListNode<E, T> down;

        public SkipListNode(Entity<E> entity, T score) {
            this.entity = entity;
            this.score = score;
        }

        @Override
        public String toString() {
            return "SkipListNode{" + "score=" + score + '}';
        }

        static class Entity<E> {
            final E val;
            Entity<E> next;

            public Entity(E val, Entity<E> next) {
                this.val = val;
                this.next = next;
            }

            List<E> toList() {
                List<E> list = new ArrayList<>();
                list.add(val);
                for (Entity<E> e = next; e != null; e = e.next) {
                    list.add(e.val);
                }
                return list;
            }

            @Override
            public String toString() {
                return "Entity{" + "val=" + val + '}';
            }
        }
    }
}
