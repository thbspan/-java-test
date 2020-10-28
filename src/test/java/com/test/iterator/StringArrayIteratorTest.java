package com.test.iterator;

import org.junit.jupiter.api.Test;

public class StringArrayIteratorTest {

    @Test
    public void test() {
        Container container = new NameRepository();
        Iterator iterator = container.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
