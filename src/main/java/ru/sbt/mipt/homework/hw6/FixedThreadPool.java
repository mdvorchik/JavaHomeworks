package ru.sbt.mipt.homework.hw6;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final Queue<Runnable> taskList = new ArrayDeque<>();
    private final List<ThreadConsumer> threadList = new ArrayList<>();

    public FixedThreadPool(int threadsCount) {
        for (int i = 0; i < threadsCount; ++i) {
            ThreadConsumer thread = new ThreadConsumer();
            threadList.add(thread);
        }
    }

    @Override
    public void start() {
        threadList.forEach(ThreadConsumer::start);
    }

    public void stop() {
        threadList.forEach(ThreadConsumer::stopThread);
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (taskList) {
            taskList.add(runnable);
            taskList.notify();
        }
    }

    private class ThreadConsumer extends Thread {
        private boolean isRunning = true;

        public void stopThread() {
            isRunning = false;
        }

        @Override
        public void run() {
            while (isRunning) {
                Runnable task;
                synchronized (taskList) {
                    while (taskList.isEmpty()) {
                        try {
                            taskList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = taskList.poll();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}

