package ru.sbt.mipt.homework.hw4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleEntitiesStorage<E> implements BankEntitiesStorage<E> {
    private final Map<String, E> storage = new HashMap<>();
    private final KeyExtractor keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public void save(E entity) {
        storage.put((String) keyExtractor.extract(entity), entity);
    }

    @Override
    public void saveAll(List<? extends E> entities) {
        entities.forEach(this::save);
    }

    @Override
    public E findByKey(Object key) {
        return storage.get(key);
    }

    @Override
    public List<E> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteByKey(Object key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(List<? extends E> entities) {
        entities.stream()
                .map((entity) -> keyExtractor.extract(entity))
                .forEach(this::deleteByKey);
    }
}
