package com.test.junit.mock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MockitoTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVerify() {
        @SuppressWarnings("unchecked")
        List<String> mockedList = Mockito.mock(List.class);

        mockedList.add("one");
        mockedList.clear();
        System.out.println(mockedList.size());
        // 验证list调用该add的操作行为
        Mockito.verify(mockedList).add("one");
        Mockito.verify(mockedList).clear();

        // 使用内建的anyInt()参数匹配器匹配索引，并存根
        Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("element");
        // 打印 element
        System.out.println(mockedList.get(888));
        System.out.println(Mockito.verify(mockedList).get(Mockito.anyInt()));
    }

    @Test
    public void testMock() {
        @SuppressWarnings("unchecked")
        LinkedList<String> mockedList = Mockito.mock(LinkedList.class);

        Mockito.when(mockedList.get(0)).thenReturn("first");
        Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException());
        System.out.println(mockedList.get(0));
        // 抛出异常
//        System.out.println(mockedList.get(1));

        // print null因为get(999)没有存根
        System.out.println(mockedList.get(999));
        Mockito.doThrow(new RuntimeException()).when(mockedList).clear();
        // 抛出RuntimeException
        mockedList.clear();
    }

    @Test
    public void testContinuousInvocation() {
        @SuppressWarnings("unchecked")
        List<String> mockedList = Mockito.mock(List.class);
        // 连续存根
        Mockito.when(mockedList.get(0)).thenReturn("Test").thenReturn("foo");
        // 连续存根的另外一个版本，更佳简便
        Mockito.when(mockedList.get(1)).thenReturn("one", "two", "three");
        // 第一次 打印 Test
        System.out.println(mockedList.get(0));
        // 第二次 打印 foo
        System.out.println(mockedList.get(0));
        // 任意连续调用 打印 foo (最后的存根生效)
        System.out.println(mockedList.get(0));
        for (int i = 0; i < 4; i++) {
            System.out.println(mockedList.get(1));
        }

        // answer
        Mockito.when(mockedList.get(2)).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return "called with arguments:" + Arrays.toString(args);
        });
        System.out.println(mockedList.get(2));
    }

    @Test
    public void testArgument() {
        @SuppressWarnings("unchecked")
        List<String> mockedList = Mockito.mock(List.class);

        Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("element");
        System.out.println(mockedList.get(999));
        System.out.println(mockedList.get(888));
        // Mockito.verify 校验方法是否被调用
        System.out.println(Mockito.verify(mockedList).get(888));
//        System.out.println(Mockito.verify(mockedList).get(Mockito.anyInt()));
        System.out.println(Mockito.verify(mockedList, Mockito.times(2)).get(Mockito.anyInt()));
    }

    @Test
    public void testArgumentMatch() {
        @SuppressWarnings("unchecked")
        Map<Integer, Object> mockedMap = Mockito.mock(Map.class);

        System.out.println(mockedMap.put(1, "test"));

        // 错误的使用方式 Mockito.verify(mockedMap).put(Mockito.anyInt(), "test");
        Mockito.verify(mockedMap).put(Mockito.anyInt(), Mockito.eq("test"));
    }
}
