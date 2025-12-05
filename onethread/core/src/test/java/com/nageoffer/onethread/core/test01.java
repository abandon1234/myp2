package com.nageoffer.onethread.core;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class test01 {

    @Test
    void demo() {
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(64, 64, 0,
                        TimeUnit.MINUTES, new ArrayBlockingQueue<>(34));

        for (int i = 0; i < 100; i++) {
            CountDownLatch countDownLatch = new CountDownLatch(34);
            for (int j = 0; j < 34; j++) {
                executor.execute(() -> {
                    try {
                        Thread.sleep(10);
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            try {
                countDownLatch.await();
                System.out.println("i = " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}