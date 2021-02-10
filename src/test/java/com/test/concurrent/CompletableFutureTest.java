package com.test.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CompletableFutureTest {

    @Test
    public void testCompletedFuture() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");

        assertTrue(cf.isDone());
        assertEquals(cf.getNow(null), "message");
    }

    @Test
    public void testRunAsync() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
        });
        assertFalse(cf.isDone());
        assertFalse(cf.isCancelled());
        assertFalse(cf.isCompletedExceptionally());
    }

    @Test
    public void testWhenComplete() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
        });
        cf.whenComplete((v, e) -> {
            System.out.println("whenComplete");
        });
        System.out.println("done");
    }

    @Test
    public void testThenApplyOrAsync() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            assertFalse(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        assertEquals("MESSAGE", cf.getNow(null));

        // async
        cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        });
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    static ExecutorService executorService = Executors.newFixedThreadPool(3, new ThreadFactory() {
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "custom-executor-" + threadNumber.getAndIncrement());
        }
    });

    @Test
    public void testThenApplyAsyncWithExecutor() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            assertFalse(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        }, executorService);
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    @Test
    public void testThenAcceptOrAsync() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(result::append);
        assertTrue(result.length() > 0, "Result was empty");

        // async
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(result::append);
        cf.join();
        assertTrue(result.length() > 0, "Result was empty");
    }

    @Test
    public void testCompleteExceptionallyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> (th != null) ? "message upon cancel" : "");

        cf.completeExceptionally(new RuntimeException("completed exceptionally"));

        assertTrue(cf.isCompletedExceptionally(), "Was not completed exceptionally");
        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch (CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }
        assertEquals("message upon cancel", exceptionHandler.join());
    }

    @Test
    public void testCancel() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message" + throwable.getLocalizedMessage());
        assertTrue(cf.cancel(true));
        assertTrue(cf.isCompletedExceptionally());
        assertTrue(cf.isCancelled());
        assertEquals("canceled message", cf2.join());
    }

    /**
     * 两个只要有一个完成
     */
    @Test
    public void testAcceptEither() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(CompletableFutureTest::delayedUpperCase)
//                .acceptEitherAsync()
                .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(CompletableFutureTest::delayedLowerCase),
                        s -> result.append(s).append("acceptEither"));
        // 不能去掉，thenApplyAsync 前面已经异步了
        cf.join();
        System.out.println(result);
        assertTrue(result.toString().endsWith("acceptEither"), "Result was empty");
    }

    @Test
    public void testRunAfterBoth() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original)
                .thenApply(String::toUpperCase)
                .runAfterBoth(CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                        () -> result.append("done"));
        System.out.println(result);
        assertTrue(result.length() > 0);
    }

    @Test
    public void testAcceptBoth() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(original)
                .thenApply(String::toUpperCase)
//                .thenAcceptBothAsync()
                .thenAcceptBoth(CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                        (s1, s2) -> result.append(s1).append(s2));
//        cf.join();
        assertEquals("MESSAGEmessage", result.toString());
    }

    @Test
    public void testThenCombine() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
                .thenApply(CompletableFutureTest::delayedUpperCase)
                .thenCombine(CompletableFuture.completedFuture(original).thenApply(CompletableFutureTest::delayedLowerCase),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.getNow(null));
    }

    @Test
    public void testThenCombineAsync() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(CompletableFutureTest::delayedUpperCase)
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(CompletableFutureTest::delayedLowerCase),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.join());
    }

    @Test
    public void thenComposeExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
                .thenApply(CompletableFutureTest::delayedUpperCase)
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(CompletableFutureTest::delayedLowerCase)
                        .thenApply(s -> upper + s));
        assertEquals("MESSAGEmessage", cf.join());
    }

    @Test
    public void testAnyOf() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");

        CompletableFuture.anyOf(messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(CompletableFutureTest::delayedUpperCase))
                .toArray(CompletableFuture[]::new)).whenComplete((res, th) -> {
            if (th == null) {
                assertTrue(isUpperCase((String) res));
                result.append(res);
            }
        });
        System.out.println(result);
        assertTrue(result.length() > 0, "Result was empty");
    }

    @Test
    public void testAllOfOrAsync() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg)
                        // .thenApplyAsync(CompletableFutureTest::delayedUpperCase)
                        .thenApply(CompletableFutureTest::delayedUpperCase))
                .collect(Collectors.toList());
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
            result.append("done");
        });
//        allOf.join();
        System.out.println(allOf.isDone());
        System.out.println(result);
        assertTrue(result.length() > 0, "Result was empty");
    }

    private boolean isUpperCase(String res) {
        return res.chars().allMatch(Character::isUpperCase);
    }

    private static String delayedLowerCase(String s) {
        randomSleep();
        return s.toLowerCase();
    }

    private static String delayedUpperCase(String s) {
        randomSleep();
        return s.toUpperCase();
    }

    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
