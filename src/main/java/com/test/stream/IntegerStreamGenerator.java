package com.test.stream;

public class IntegerStreamGenerator {

    public static MyStream<Integer> getIntegerStream(int low, int high) {
        return getIntegerStream(low, high, true);
    }

    private static MyStream<Integer> getIntegerStream(int low, int high, boolean isFirst) {
        if (low > high) {
            return MyStream.empty();
        }
        if (isFirst) {
            return new MyStreamImpl.Builder<Integer>()
                    .nextItemEvalProcess(new NextItemEvalProcess<>(() -> getIntegerStream(low, high, false)))
                    .build();
        } else {
            return new MyStreamImpl.Builder<Integer>()
                    .head(low)
                    .nextItemEvalProcess(new NextItemEvalProcess<>(() -> getIntegerStream(low + 1, high, false)))
                    .build();
        }
    }
}
