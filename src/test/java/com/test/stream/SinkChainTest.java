package com.test.stream;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

public class SinkChainTest {

    @Test
    public void testSink() {
        int[] source = {1, 2, 3, 4, 5};
        Spliterator.OfInt spliterator = Arrays.stream(source).spliterator();
        Sink<Integer> sink0 = new Sink<>("source sink", null);
        Sink<Integer> sink4 = sink0.op("sink1").op("sink2").op("sink3").op("terminal sink");

        Sink<Integer> wrappedSink = wrapSink(sink4);
        spliterator.forEachRemaining(wrappedSink);
    }

    private Sink<Integer> wrapSink(Sink<Integer> sink) {
        while (sink.upstream != null) {
            sink.upstream.downstream = sink;
            sink = sink.upstream;
        }
        return sink;
    }

    static class Sink<T> implements Consumer<T> {

        private final Sink<T> upstream;
        private Sink<T> downstream;
        private final String name;

        public Sink(String name, Sink<T> upstream) {
            this.name = name;
            this.upstream = upstream;
        }

        public Sink<T> op(String name) {
            return new Sink<>(name, this);
        }
        @Override
        public void accept(T t) {
            System.out.println(name + " handles " + t);
            if (downstream != null) {
                downstream.accept(t);
            }
        }
    }
}
