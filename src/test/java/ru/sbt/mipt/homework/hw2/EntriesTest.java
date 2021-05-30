package ru.sbt.mipt.homework.hw2;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class EntriesTest {

    @Test
    public void addEntryOnce() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        Entry entry = entryList.get(0);
        //when
        entries.addEntry(entry);
        //verify
        assertEquals(entry.getTime(), entries.last().getTime());
    }

    @Test
    public void addEntryTwice() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        Entry entry1 = entryList.get(0);
        Entry entry2 = entryList.get(1);
        //when
        entries.addEntry(entry1);
        entries.addEntry(entry2);
        //verify
        assertEquals(entry2.getTime(), entries.last().getTime());
    }

    @Test
    public void fromMiddleTime() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        List<Entry> expectedList = new ArrayList<>();
        expectedList.add(entryList.get(2));
        expectedList.add(entryList.get(3));
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.from(entryList.get(1).getTime().plusDays(3).toLocalDate());
        //verify
        assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void fromWithNoElement() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.from(entryList.get(3).getTime().plusDays(30).toLocalDate());
        //verify
        assertArrayEquals(Collections.EMPTY_LIST.toArray(), entriesList.toArray());
    }

    @Test
    public void fromVeryEarlyTime() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        List<Entry> expectedList = new ArrayList<>(entryList);
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.from(entryList.get(0).getTime().minusDays(30).toLocalDate());
        //verify
        assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void fromFirstElementTime() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        List<Entry> expectedList = new ArrayList<>(entryList);
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.from(entryList.get(0).getTime().toLocalDate());
        //verify
        assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenDatesAllEntries() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        List<Entry> expectedList = new ArrayList<>(entryList);
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.betweenDates(entryList.get(0).getTime().minusDays(30).toLocalDate(),
                entryList.get(3).getTime().plusDays(100).toLocalDate());
        //verify
        assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenDatesOneEntry() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        List<Entry> expectedList = new ArrayList<>();
        expectedList.add(entryList.get(2));
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.betweenDates(entryList.get(1).getTime().plusDays(1).toLocalDate(),
                entryList.get(2).getTime().plusDays(1).toLocalDate());
        //verify
        assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenDatesSomeEntries() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        List<Entry> expectedList = new ArrayList<>();
        expectedList.add(entryList.get(0));
        expectedList.add(entryList.get(1));
        expectedList.add(entryList.get(2));
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.betweenDates(entryList.get(0).getTime().toLocalDate(),
                entryList.get(2).getTime().toLocalDate()); //plus 1
        //verify
        assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenWithNoEntries() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        fillEntries(entries, entryList);
        //when
        List<Entry> entriesList = (ArrayList<Entry>) entries.betweenDates(entryList.get(2).getTime().plusDays(1).toLocalDate(),
                entryList.get(3).getTime().minusDays(1).toLocalDate());
        //verify
        assertArrayEquals(Collections.EMPTY_LIST.toArray(), entriesList.toArray());
    }

    @Test
    public void lastWhenAddEntryOnce() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        Entry entry = entryList.get(0);
        //when
        entries.addEntry(entry);
        //verify
        assertEquals(entry, entries.last());
    }

    @Test
    public void lastWhenAddEntryTwice() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        Entry entry1 = entryList.get(0);
        Entry entry2 = entryList.get(1);
        //when
        entries.addEntry(entry1);
        entries.addEntry(entry2);
        //verify
        assertEquals(entry2, entries.last());
    }

    @Test
    public void firstWhenAddEntryOnce() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        Entry entry = entryList.get(0);
        //when
        entries.addEntry(entry);
        //verify
        assertEquals(entry, entries.first());
    }

    @Test
    public void firstWhenAddEntryTwice() {
        //given
        Entries entries = new Entries(new TreeMap<>());
        List<Entry> entryList = setUpEntryList();
        Entry entry1 = entryList.get(0);
        Entry entry2 = entryList.get(1);
        //when
        entries.addEntry(entry1);
        entries.addEntry(entry2);
        //verify
        assertEquals(entry1, entries.first());
    }

    private TransactionManager prepareTransactionManager() {
        return new TransactionManager(new HashMap<>(), new ArrayList<>());
    }

    private List<Account> prepareAccountList(TransactionManager transactionManager) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, transactionManager));
        accounts.add(new Account(2, transactionManager));
        return accounts;
    }

    private List<Transaction> prepareTransactionList(TransactionManager transactionManager, List<Account> accounts) {
        List<Transaction> transactions = new ArrayList<>();
        if (accounts.size() > 1) {
            transactions.add(transactionManager.createTransaction(333, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(-777, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(-222, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(888, accounts.get(1), accounts.get(0)));
        }
        return transactions;
    }

    private List<Entry> prepareEntryList(List<Transaction> transactionList, List<Account> accountList) {
        List<Entry> entries = new ArrayList<>();
        if (transactionList.size() > 3 && accountList.size() > 0) {
            entries.add(new Entry(accountList.get(0), transactionList.get(0), transactionList.get(0).getAmount(), LocalDateTime.now()));
            entries.add(new Entry(accountList.get(0), transactionList.get(1), transactionList.get(1).getAmount(), LocalDateTime.now().plusMinutes(1)));
            entries.add(new Entry(accountList.get(0), transactionList.get(2), transactionList.get(2).getAmount(), LocalDateTime.now().plusDays(5)));
            entries.add(new Entry(accountList.get(0), transactionList.get(3), transactionList.get(3).getAmount(), LocalDateTime.now().plusDays(15)));
        }
        return entries;
    }

    private List<Entry> setUpEntryList() {
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, accountList);
        return prepareEntryList(transactionList, accountList);
    }

    private void fillEntries(Entries entries, List<Entry> entryList) {
        for (Entry entry : entryList) {
            entries.addEntry(entry);
        }
    }
}