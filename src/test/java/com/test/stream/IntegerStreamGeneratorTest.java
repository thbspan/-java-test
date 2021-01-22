package com.test.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class IntegerStreamGeneratorTest {

    @Test
    public void testMap() {
        // 测试stream类
        MyStream<Integer> integerStream = IntegerStreamGenerator.getIntegerStream(10, 100_0000);
        integerStream.map(String::valueOf)
                .filter(s -> s.contains("2"))
                .forEach(System.out::println);
    }

    @Test
    public void testFlatMap() {
        MyStream<Integer> integerStream = IntegerStreamGenerator.getIntegerStream(10, 300_00);
        integerStream
                .peek(integer -> {
                    System.out.println();
                    System.out.println(integer);
                })
                .map(integer -> Arrays.asList(String.valueOf(integer).split("")))
//                .filter(strings -> strings.size() >= 5)
                .flatMap(CollectionStreamGenerator::getListStream)
                .forEach(s -> System.out.print(" " + s));
    }

    @Test
    public void testDistinct() {
        MyStream<String> stream =
                CollectionStreamGenerator.getListStream(Arrays.asList("a", "b", "c", "d", "d", "e", "e"));
        stream.distinct()
                .forEach(System.out::println);

    }

    @Test
    public void testCollector() {
        MyStream<Integer> integerStream = IntegerStreamGenerator.getIntegerStream(10, 100);
        List<Integer> integers = integerStream.limit(50)
                .collect(Collectors.toList());
        System.out.println(integers);
    }

    @Test
    public void testCount() {
        System.out.println(IntegerStreamGenerator.getIntegerStream(10, 100_0000).count());
    }
}
