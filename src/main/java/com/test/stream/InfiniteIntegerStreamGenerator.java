package com.test.stream;

import java.util.function.Supplier;

/**
 * 无穷的整数流
 */
public class InfiniteIntegerStreamGenerator {

    public static MyStream<Integer> getInfiniteIntegerStream(Supplier<Integer> supplier) {
        return getInfiniteIntegerStream(supplier, true);
    }

    private static MyStream<Integer> getInfiniteIntegerStream(Supplier<Integer> supplier, boolean isFirst) {
        if (isFirst) {
            return new MyStreamImpl.Builder<Integer>()
                    .nextItemEvalProcess(new NextItemEvalProcess<>(() -> getInfiniteIntegerStream(supplier, false)))
                    .build();
        } else {
            return new MyStreamImpl.Builder<Integer>()
                    .head(supplier.get())
                    .nextItemEvalProcess(new NextItemEvalProcess<>(() -> getInfiniteIntegerStream(supplier, false)))
                    .build();
        }
    }
}
