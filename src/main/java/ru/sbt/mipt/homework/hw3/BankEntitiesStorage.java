package ru.sbt.mipt.homework.hw3;

import java.util.List;

public interface BankEntitiesStorage<E> {
    void save(E entity);

    void saveAll(List<? extends E> entities);

    E findByKey(Object key);

    List<E> findAll();

    void deleteByKey(Object key);

    void deleteAll(List<? extends E> entities);
}
