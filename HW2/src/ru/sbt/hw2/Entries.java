package ru.sbt.hw2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {

    /**
     * entries must be sorted by LocalDateTime
     */
    private ArrayList<Entry> entries;

    private int binSearchByLocalDate(LocalDate key, int lowIndex, int highIndex){
        int currentIndex = -1;
        while (lowIndex <= highIndex) {
            int mid = (lowIndex + highIndex) / 2;
            if (entries.get(mid).getTime().toLocalDate().compareTo(key) < 0) {
                lowIndex = mid + 1;
            } else if (entries.get(mid).getTime().toLocalDate().compareTo(key) > 0) {
                highIndex = mid - 1;
            } else if (entries.get(mid).getTime().toLocalDate().compareTo(key) == 0) {
                currentIndex = mid;
                break;
            }
        }
        return currentIndex;
    }

    private ArrayList<Entry> copyFromEntriesToArrayByIndexes(int lowIndex, int highIndex) {
        ArrayList<Entry> arrayList = new ArrayList<>(highIndex - lowIndex + 1);
        for (int i = lowIndex, arrayIndex = 0; i < highIndex; ++i, ++arrayIndex){
            arrayList.set(arrayIndex, entries.get(i));
        }
        return arrayList;
    }

    public Entries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    void addEntry(Entry entry) {
        if (entry != null) entries.add(entry);
    }

    Collection<Entry> from(LocalDate date) {
        Collection<Entry> entryCollection;
        int indexOfFirstElementByLocalDate = binSearchByLocalDate(date, 0, entries.size() - 1);
        if (indexOfFirstElementByLocalDate == -1) return new ArrayList<>();
        entryCollection = copyFromEntriesToArrayByIndexes(indexOfFirstElementByLocalDate, entries.size() - 1);
        return  entryCollection;
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        Collection<Entry> entryCollection;
        int indexOfFirstElementByLocalDate = binSearchByLocalDate(from, 0, entries.size() - 1);
        if (indexOfFirstElementByLocalDate == -1) return new ArrayList<>();
        int indexOfLastElementByLocalDate = binSearchByLocalDate(to.plusDays(1), indexOfFirstElementByLocalDate, entries.size() - 1);
        if (indexOfLastElementByLocalDate == -1 || indexOfLastElementByLocalDate == 0) return new ArrayList<>();
        entryCollection = copyFromEntriesToArrayByIndexes(indexOfFirstElementByLocalDate, indexOfLastElementByLocalDate);
        return  entryCollection;
    }

    Entry last() {
        if (entries != null && !entries.isEmpty()) return entries.get(entries.size()-1);
        return null;
    }
}
