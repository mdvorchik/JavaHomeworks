package ru.sbt.mipt.homework.hw6;

/*
в конструкторе задается минимальное и максимальное (int min, int max) число потоков,
количество запущенных потоков может быть увеличено от минимального к максимальному,
 если при добавлении нового задания в очередь нет свободного потока для исполнения этого задания.
 При отсутствии задания в очереди, количество потоков опять должно быть уменьшено до значения min
 */
public class ScalableThreadPool implements ThreadPool {
    @Override
    public void start() {

    }

    @Override
    public void execute(Runnable runnable) {

    }
}
