package ru.sbt.mipt.homework.hw3;

public interface KeyExtractor<T> {
    Object extract(T entity);
}
