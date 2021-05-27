package ru.sbt.mipt.homework.hw2;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {

    /**
     * entries must be sorted by LocalDateTime
     */
    private final List<Entry> entries;
    private final Map<LocalDate, List<Entry>> entriesMultiMap = new TreeMap<>();

    public Entries(ArrayList<Entry> entries) {
        this.entries = entries;
        for (Entry entry : entries) {
            entriesMultiMap.computeIfPresent(entry.getTime().toLocalDate(), (key, value) -> {
                value.add(entry);
                return value;
            });
            entriesMultiMap.putIfAbsent(entry.getTime().toLocalDate(), new ArrayList<>(Arrays.asList(entry)));
        }
    }

    void addEntry(Entry entry) {
        if (entry != null) {
            entries.add(entry);
            entriesMultiMap.computeIfPresent(entry.getTime().toLocalDate(), (key, value) -> {
                value.add(entry);
                return value;
            });
            entriesMultiMap.putIfAbsent(entry.getTime().toLocalDate(), new ArrayList<>(Arrays.asList(entry)));
        }
    }

    Collection<Entry> from(LocalDate date) {
        Map<LocalDate, List<Entry>> tempMap = entriesMultiMap.entrySet().stream()
                .filter((map) -> map.getKey().compareTo(date) >= 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Entry> treeSet = new ArrayList<>();
        tempMap.entrySet().stream().forEach(t -> treeSet.addAll(t.getValue()));
        return treeSet.stream().sorted().collect(Collectors.toList());
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        Map<LocalDate, List<Entry>> tempMap = entriesMultiMap.entrySet().stream()
                .filter((map) -> map.getKey().compareTo(from) >= 0 && map.getKey().compareTo(to) <= 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Entry> treeSet = new ArrayList<>();
        tempMap.entrySet().stream().forEach(t -> treeSet.addAll(t.getValue()));
        return treeSet.stream().sorted().collect(Collectors.toList());
    }

    Entry last() {
        if (entries != null && !entries.isEmpty()) return entries.get(entries.size() - 1);
        return null;
    }

    Entry first() {
        if (entries != null && !entries.isEmpty()) return entries.get(0);
        return null;
    }
}
