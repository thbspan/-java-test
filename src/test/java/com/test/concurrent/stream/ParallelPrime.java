package com.test.concurrent.stream;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

public class ParallelPrime {
    static final int COUNT = 100_000;
    public static boolean isPrime(long n) {
        return LongStream.rangeClosed(2, (long)Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }
    @Test
    public void test() throws IOException {
        List<String> primes =
                LongStream.iterate(2, i -> i + 1)
                        .parallel()              // [1]
                        .filter(ParallelPrime::isPrime)
                        .limit(COUNT)
                        .mapToObj(Long::toString)
                        .collect(Collectors.toList());

//        Files.write(Paths.get("primes.txt"), primes, StandardOpenOption.CREATE);
        primes.forEach(System.out::println);
    }
}
