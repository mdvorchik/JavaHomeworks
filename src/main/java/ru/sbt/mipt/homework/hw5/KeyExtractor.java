package ru.sbt.mipt.homework.hw5;

public interface KeyExtractor<T> {
    Object extract(T entity);
}
