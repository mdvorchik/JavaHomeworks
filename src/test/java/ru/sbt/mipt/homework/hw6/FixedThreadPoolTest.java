package ru.sbt.mipt.homework.hw6;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class FixedThreadPoolTest {
    static AtomicInteger finishedThreadCount = new AtomicInteger(0);

    @Test
    public void execute() throws InterruptedException {
        //given
        int availableProcessorsCount = Runtime.getRuntime().availableProcessors();
        FixedThreadPool threadPool = new FixedThreadPool(availableProcessorsCount);
        Runnable task = () -> {
            try {
                Thread.sleep(1000);
                finishedThreadCount.incrementAndGet();
                System.out.println("Task done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        //when
        threadPool.start();
        for (int i = 0; i < availableProcessorsCount; ++i) {
            threadPool.execute(task);
        }
        Thread.sleep(1500);
        //verify
        assertEquals(availableProcessorsCount, finishedThreadCount.get());
    }
}