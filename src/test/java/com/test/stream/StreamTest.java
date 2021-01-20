package com.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StreamTest {

    @Test
    public void testFlatMap() {
        List<String> words = Arrays.asList("Hello", "World");
        Assertions.assertLinesMatch(words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList()), Arrays.asList("H", "e", "l", "o", "W", "r", "d"));
    }

    @Test
    public void testGenerate() {
        Stream.generate(() -> UUID.randomUUID().toString())
                .limit(10)
                .forEach(System.out::println);
        IntStream.generate(new Random()::nextInt)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void testJoin() {
        List<String> words = Arrays.asList("Hello", "World", "Stream", "Test");
        System.out.println(words.stream().collect(Collectors.joining(" ")));
    }

    @Test
    public void testCustomCollectorList() {
        // 输入类型 累加器类型 最终返回类型
        Collector<String, List<String>, List<String>> myListCollector = new Collector<>() {
            @Override
            public Supplier<List<String>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<String>, String> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<List<String>> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left;
                };
            }

            @Override
            public Function<List<String>, List<String>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
            }
        };
        List<String> strings = Stream.generate(() -> UUID.randomUUID().toString())
                .parallel()
                .limit(10)
                .collect(myListCollector);
        System.out.println(strings);
    }

    @Test
    public void testCustomCollectorIntSummaryStatistics() {
        IntSummaryStatistics statistics = Stream.generate(new Random()::nextInt)
                .limit(100)
                .collect(summarizingInt(Integer::intValue));
        System.out.println("count:" + statistics.count);
        System.out.println("sum:" + statistics.sum);
        System.out.println("max:" + statistics.max);
        System.out.println("min:" + statistics.min);
    }

    private static <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper) {
        return new Collector<T, IntSummaryStatistics, IntSummaryStatistics>() {
            @Override
            public Supplier<IntSummaryStatistics> supplier() {
                return IntSummaryStatistics::new;
            }

            @Override
            public BiConsumer<IntSummaryStatistics, T> accumulator() {
                return (r, t) -> r.accept(mapper.applyAsInt(t));
            }

            @Override
            public BinaryOperator<IntSummaryStatistics> combiner() {
                return (l, r) -> {
                    l.combine(r);
                    return l;
                };
            }

            @Override
            public Function<IntSummaryStatistics, IntSummaryStatistics> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
            }
        };
    }

    static class IntSummaryStatistics implements IntConsumer {
        private long count;
        private long sum;
        private int min = Integer.MAX_VALUE;
        private int max = Integer.MIN_VALUE;

        @Override
        public void accept(int value) {
            ++count;
            sum += value;
            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        public void combine(IntSummaryStatistics other) {
            count += other.count;
            sum += other.sum;
            min = Math.min(min, other.min);
            max = Math.max(max, other.max);
        }
    }

    @Test
    public void testCustomCollectorIntCollector() {
        ArrayList<Integer> list = IntStream.generate(new Random()::nextInt)
                .limit(100)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(list);
    }
}
