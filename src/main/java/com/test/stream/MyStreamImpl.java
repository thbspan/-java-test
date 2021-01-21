package com.test.stream;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class MyStreamImpl<T> implements MyStream<T> {

    private T head;

    private NextItemEvalProcess<T> nextItemEvalProcess;

    private boolean isEnd;

    public static class Builder<T> {
        private final MyStreamImpl<T> target;

        public Builder() {
            this.target = new MyStreamImpl<>();
        }

        public Builder<T> head(T head) {
            target.head = head;
            return this;
        }

        Builder<T> isEnd(boolean isEnd) {
            target.isEnd = isEnd;
            return this;
        }

        public Builder<T> nextItemEvalProcess(NextItemEvalProcess<T> nextItemEvalProcess) {
            target.nextItemEvalProcess = nextItemEvalProcess;
            return this;
        }

        public MyStream<T> build() {
            return target;
        }
    }

    @Override
    public <R> MyStream<R> map(Function<T, R> mapper) {
        return new Builder<R>()
                .nextItemEvalProcess(new NextItemEvalProcess<>(() -> map(mapper, nextItemEvalProcess.eval())))
                .build();
    }

    private static <R, T> MyStream<R> map(Function<T, R> mapper, MyStream<T> myStream) {
        if (myStream.isEmptyStream()) {
            return MyStream.empty();
        }
        MyStreamImpl<T> stream = (MyStreamImpl<T>) myStream;
        R head = mapper.apply(stream.head);

        return new Builder<R>()
                .head(head)
                .nextItemEvalProcess(new NextItemEvalProcess<>(() -> map(mapper, stream.eval())))
                .build();
    }

    @Override
    public <R> MyStream<R> flatMap(Function<T, ? extends MyStream<R>> mapper) {

        return new Builder<R>()
                .nextItemEvalProcess(new NextItemEvalProcess<>(() -> {
                    System.out.println("flat map test");
                    return flatMap(mapper, MyStream.empty(), this);
                }))
                .build();
    }

    private static <R, T> MyStream<R> flatMap(Function<T, ? extends MyStream<R>> mapper, MyStream<R> headMyStream, MyStream<T> myStream) {
        MyStream<R> currentHeadMyStream = headMyStream;
        MyStream<T> currentMyStream = myStream;
        while (currentHeadMyStream.isEmptyStream()) {
            if (currentMyStream.isEmptyStream()) {
                return currentHeadMyStream;
            }
            MyStreamImpl<T> stream = (MyStreamImpl<T>) ((MyStreamImpl<T>) currentMyStream).eval();
            if (stream.isEmptyStream()) {
                return MyStream.empty();
            }
            currentHeadMyStream = ((MyStreamImpl<R>) mapper.apply(stream.head)).eval();
            currentMyStream = stream;
        }
        final MyStream<R> finalCurrentHeadMyStream = currentHeadMyStream;
        final MyStream<T> finalCurrentMyStream = currentMyStream;
        R outHead = ((MyStreamImpl<R>) finalCurrentHeadMyStream).head;
        return new Builder<R>()
                .head(outHead)
                .nextItemEvalProcess(new NextItemEvalProcess<>(() -> flatMap(mapper, ((MyStreamImpl<R>) finalCurrentHeadMyStream).eval(), finalCurrentMyStream)))
                .build();

    }

    @Override
    public MyStream<T> filter(Predicate<T> predicate) {
        NextItemEvalProcess<T> lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess<>(() -> {
            MyStream<T> myStream = lastNextItemEvalProcess.eval();
            return filter(predicate, myStream);
        });
        return this;
    }

    private static <T> MyStream<T> filter(Predicate<T> predicate, MyStream<T> myStream) {
        MyStreamImpl<T> stream = (MyStreamImpl<T>) myStream;
        for (; !stream.isEmptyStream(); stream = (MyStreamImpl<T>) stream.eval()) {
            final MyStreamImpl<T> currentStream = stream;
            T head = stream.head;
            if (predicate.test(head)) {
                return new Builder<T>()
                        .head(head)
                        .nextItemEvalProcess(new NextItemEvalProcess<>(() -> filter(predicate, currentStream.eval())))
                        .build();
            }
        }
        return stream;
    }

    @Override
    public MyStream<T> limit(int n) {
        NextItemEvalProcess<T> lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess<>(() -> limit(n, lastNextItemEvalProcess.eval()));
        return this;
    }

    private static <T> MyStream<T> limit(int num, MyStream<T> myStream) {
        if (num <= 0 || myStream.isEmptyStream()) {
            return MyStream.empty();
        }
        MyStreamImpl<T> stream = (MyStreamImpl<T>) myStream;
        return new Builder<T>()
                .head(stream.head)
                .nextItemEvalProcess(new NextItemEvalProcess<>(() -> limit(num, stream.eval())))
                .build();
    }

    @Override
    public MyStream<T> distinct() {
        NextItemEvalProcess<T> lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess<>(
                () -> distinct(new HashSet<>(), lastNextItemEvalProcess.eval()));
        return this;
    }

    private MyStream<T> distinct(Set<T> set, MyStream<T> myStream) {
        if (myStream.isEmptyStream()) {
            return myStream;
        }
        MyStreamImpl<T> stream = (MyStreamImpl<T>) myStream;

        while (set.contains(stream.head)) {
            MyStream<T> eval = stream.eval();
            stream = ((MyStreamImpl<T>) eval);
            if (stream.isEmptyStream()) {
                return stream;
            }
        }
        final MyStreamImpl<T> finalStream = stream;
        set.add(stream.head);
        return new Builder<T>()
                .head(stream.head)
                .nextItemEvalProcess(new NextItemEvalProcess<>(() -> distinct(set, finalStream.eval())))
                .build();
    }

    @Override
    public MyStream<T> peek(Consumer<? super T> action) {
        NextItemEvalProcess<T> lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess<>(() -> peek(action, lastNextItemEvalProcess.eval()));
        return this;
    }

    private static <T> MyStream<T> peek(Consumer<? super T> action, MyStream<T> myStream) {
        if (myStream.isEmptyStream()) {
            return MyStream.empty();
        }

        MyStreamImpl<T> stream = ((MyStreamImpl<T>) myStream);
        T head = stream.head;
        action.accept(head);
        return new Builder<T>()
                .head(head)
                .nextItemEvalProcess(new NextItemEvalProcess<>(() -> peek(action, stream.eval())))
                .build();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        // 终结操作 直接开始求值
        // 头节点是空的 this.eval() 跳过第一个
        for (MyStreamImpl<T> initStream = (MyStreamImpl<T>) this.eval();
             !initStream.isEmptyStream(); initStream = (MyStreamImpl<T>) initStream.eval()) {
            action.accept(initStream.head);
        }
    }

    @Override
    public <R> R reduce(R initVal, BiFunction<R, R, T> accumulator) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<T, A, R> collector) {
        return null;
    }

    @Override
    public T max(Comparator<T> comparator) {
        return null;
    }

    @Override
    public T min(Comparator<T> comparator) {
        return null;
    }

    /**
     * 判断当前流是否为空
     */
    @Override
    public boolean isEmptyStream() {
        return this.isEnd;
    }

    /**
     * 当前流强制求值
     */
    private MyStream<T> eval() {
        return nextItemEvalProcess.eval();
    }
}
