package com.test.collection;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

public class ListIteratorTest {

    @Test
    public void testListIterator() {
        List<String> list = Arrays.asList("zs", "li", "ww");
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            System.out.print(listIterator.nextIndex() + " ");
            System.out.println(listIterator.next());
        }
        while (listIterator.hasPrevious()) {
            System.out.print(listIterator.previousIndex() + " ");
            System.out.println(listIterator.previous());
        }
    }
}
