package ru.sbt.mipt.homework.hw4;

public interface KeyExtractor<T> {
    Object extract(T entity);
}
