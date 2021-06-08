package ru.sbt.mipt.homework.hw6;

public interface KeyExtractor<T> {
    Object extract(T entity);
}
