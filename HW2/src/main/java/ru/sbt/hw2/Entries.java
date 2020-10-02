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

    private int binSearchByLocalDate(LocalDate key, int lowIndex, int highIndex, boolean isNeedFirstIndex) {
        int currentIndex = -1;
        while (lowIndex <= highIndex) {
            int mid = (lowIndex + highIndex) / 2;
            if (entries.get(mid).getTime().toLocalDate().compareTo(key) < 0) {
                lowIndex = mid + 1;
            } else if (entries.get(mid).getTime().toLocalDate().compareTo(key) > 0) {
                highIndex = mid - 1;
            } else if (entries.get(mid).getTime().toLocalDate().compareTo(key) == 0) {
                currentIndex = mid;
                if (isNeedFirstIndex) {
                    currentIndex = searchFirstIndexFrom(key, currentIndex);
                } else {
                    currentIndex = searchLastIndexFrom(key, currentIndex);
                }
                break;
            }
        }
        if (currentIndex == -1 && isNeedFirstIndex) currentIndex = searchFirstIndexFrom(key, highIndex);
        if (currentIndex == -1 && !isNeedFirstIndex) currentIndex = searchLastIndexFrom(key, lowIndex);
        return currentIndex;
    }

    private int searchFirstIndexFrom(LocalDate key, int indexFrom) {
        int currentIndex = -1;
        for (int i = indexFrom; i >= 0; --i) {
            if (!key.equals(entries.get(i).getTime().toLocalDate())){
                return i+1;
            } else {
                currentIndex = i;
            }
        }
        return currentIndex;
    }

    private int searchLastIndexFrom(LocalDate key, int indexFrom) {
        int currentIndex = -1;
        for (int i = indexFrom; i < entries.size(); ++i) {
            if (!key.equals(entries.get(i).getTime().toLocalDate())){
                return i - 1;
            } else {
                currentIndex = i;
            }
        }
        return currentIndex;
    }

    private ArrayList<Entry> copyFromEntriesToArrayByIndexes(int lowIndex, int highIndex) {
        ArrayList<Entry> arrayList = new ArrayList<>();
        for (int i = lowIndex, arrayIndex = 0; i <= highIndex; ++i, ++arrayIndex){
            arrayList.add(entries.get(i));
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
        if (date.compareTo(first().getTime().toLocalDate()) < 0) date = first().getTime().toLocalDate();
        int indexOfFirstElementByLocalDate = binSearchByLocalDate(date, 0, entries.size() - 1, true);
        if (indexOfFirstElementByLocalDate == -1) return new ArrayList<>();
        entryCollection = copyFromEntriesToArrayByIndexes(indexOfFirstElementByLocalDate, entries.size() - 1);
        return  entryCollection;
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        if (from.compareTo(to) > 0) throw new IllegalArgumentException("\"From\" must be less then \"to\"");
        if (from.compareTo(first().getTime().toLocalDate()) < 0) from = first().getTime().toLocalDate();
        if (to.compareTo(last().getTime().toLocalDate()) > 0) to = last().getTime().toLocalDate();
        Collection<Entry> entryCollection;
        int indexOfFirstElementByLocalDate = binSearchByLocalDate(from, 0, entries.size() - 1, true);
        if (indexOfFirstElementByLocalDate == -1) return new ArrayList<>();
        int indexOfLastElementByLocalDate = binSearchByLocalDate(to, indexOfFirstElementByLocalDate, entries.size() - 1, false);
        if (indexOfLastElementByLocalDate == -1 || indexOfLastElementByLocalDate == 0) return new ArrayList<>();
        entryCollection = copyFromEntriesToArrayByIndexes(indexOfFirstElementByLocalDate, indexOfLastElementByLocalDate);
        return  entryCollection;
    }

    Entry last() {
        if (entries != null && !entries.isEmpty()) return entries.get(entries.size()-1);
        return null;
    }

    Entry first() {
        if (entries != null && !entries.isEmpty()) return entries.get(0);
        return null;
    }
}
