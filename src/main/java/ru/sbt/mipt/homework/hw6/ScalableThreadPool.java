package ru.sbt.mipt.homework.hw6;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
в конструкторе задается минимальное и максимальное (int min, int max) число потоков,
количество запущенных потоков может быть увеличено от минимального к максимальному,
 если при добавлении нового задания в очередь нет свободного потока для исполнения этого задания.
 При отсутствии задания в очереди, количество потоков опять должно быть уменьшено до значения min
 */
public class ScalableThreadPool implements ThreadPool {
    private final int minThreadsCount;
    private final int maxThreadsCount;
    private final Queue<Runnable> taskList = new ArrayDeque<>();
    private final List<ThreadConsumer> threadList = new ArrayList<>();
    private final ThreadController threadController = new ThreadController();

    public ScalableThreadPool(int minThreadsCount, int maxThreadsCount) {
        this.minThreadsCount = minThreadsCount;
        this.maxThreadsCount = maxThreadsCount;
        for (int i = 0; i < maxThreadsCount; ++i) {
            ThreadConsumer thread = new ThreadConsumer();
            threadList.add(thread);
        }
    }

    @Override
    public void start() {
        for (int i = 0; i < minThreadsCount; ++i) {
            threadList.get(i).start();
        }
        threadController.start();
    }

    public void stop() {
        threadList.forEach(ThreadConsumer::stopThread);
        threadController.stopThread();
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (taskList) {
            taskList.add(runnable);
            taskList.notify();
        }
    }

    private void startExtraThreads() {
        for (int i = minThreadsCount; i < maxThreadsCount; ++i) {
            ThreadConsumer thread = new ThreadConsumer();
            threadList.set(i, thread);
            thread.start();
        }
    }

    private void stopExtraThreads() {
        for (int i = minThreadsCount; i < maxThreadsCount; ++i) {
            threadList.get(i).stopThread();
        }
    }

    private class ThreadController extends Thread {
        private boolean isRunning = true;

        public void stopThread() {
            isRunning = false;
        }

        @Override
        public void run() {
            while (isRunning) {
                int threadsBusyCount = 0;
                for (int i = 0; i < threadList.size(); ++i) {
                    if (threadList.get(i).isBusy()) threadsBusyCount++;
                }
                if (threadsBusyCount >= minThreadsCount) {
                    startExtraThreads();
                } else {
                    stopExtraThreads();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ThreadConsumer extends Thread {
        private boolean isRunning = true;
        private boolean isBusy = false;

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
                    isBusy = true;
                    task.run();
                    isBusy = false;
                }
            }
        }

        public boolean isBusy() {
            return isBusy;
        }
    }
}
