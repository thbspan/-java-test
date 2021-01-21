package com.test.stream;

import java.util.Iterator;
import java.util.List;

public class CollectionStreamGenerator {

    public static <T> MyStream<T> getListStream(List<T> list) {
        return getListStream(list.iterator(), true);
    }

    private static <T> MyStream<T> getListStream(Iterator<T> iterator, boolean isFirst) {
        if (!iterator.hasNext()) {
            // 不存在下一个元素，返回一个空对象
            return MyStream.empty();
        }
        if (isFirst) {
            return new MyStreamImpl.Builder<T>()
                    .nextItemEvalProcess(new NextItemEvalProcess<>(() -> getListStream(iterator, false)))
                    .build();
        } else {
            return new MyStreamImpl.Builder<T>()
                    .head(iterator.next())
                    .nextItemEvalProcess(new NextItemEvalProcess<>(() -> getListStream(iterator, false)))
                    .build();
        }
    }

}
