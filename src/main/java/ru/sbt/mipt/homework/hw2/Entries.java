package ru.sbt.mipt.homework.hw2;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {

    private final Map<LocalDate, List<Entry>> entriesMultiMap;

    public Entries(TreeMap<LocalDate, List<Entry>> entriesMultiMap) {
        this.entriesMultiMap = entriesMultiMap;
    }

    void addEntry(Entry entry) {
        if (entry != null) {
            entriesMultiMap.computeIfPresent(entry.getTime().toLocalDate(), (key, value) -> {
                value.add(entry);
                return value;
            });
            entriesMultiMap.putIfAbsent(entry.getTime().toLocalDate(), new ArrayList<>(Collections.singletonList(entry)));
        }
    }

    Collection<Entry> from(LocalDate date) {
        return entriesMultiMap.entrySet()
                .stream()
                .filter((map) -> map.getKey().compareTo(date) >= 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList())
                .stream().reduce((list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                })
                .orElse(new ArrayList<>());
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        return entriesMultiMap.entrySet()
                .stream()
                .filter((map) -> map.getKey().compareTo(from) >= 0 && map.getKey().compareTo(to) <= 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList())
                .stream().reduce((list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                })
                .orElse(new ArrayList<>());
    }

    Entry last() {
        if (entriesMultiMap != null && !entriesMultiMap.isEmpty()) {
            LocalDate lastKey = (LocalDate) entriesMultiMap.keySet().toArray()[entriesMultiMap.size() - 1];
            List<Entry> entryArrayList = entriesMultiMap.get(lastKey);
            return entryArrayList.get(entryArrayList.size() - 1);
        } else return null;
    }

    Entry first() {
        if (entriesMultiMap != null && !entriesMultiMap.isEmpty()) {
            LocalDate firstKey = (LocalDate) entriesMultiMap.keySet().toArray()[0];
            List<Entry> entryArrayList = entriesMultiMap.get(firstKey);
            return entryArrayList.get(0);
        } else return null;
    }
}
