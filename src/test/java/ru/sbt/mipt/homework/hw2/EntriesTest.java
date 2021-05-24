package ru.sbt.mipt.homework.hw2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class EntriesTest {

    private TransactionManager transactionManager;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Account account1;
    private Account account2;
    private Entry entry1;
    private Entry entry2;
    private Entry entry3;
    private Entry entry4;
    private LocalDateTime localDateTime1;
    private LocalDateTime localDateTime2;
    private LocalDateTime localDateTime3;
    private LocalDateTime localDateTime4;
    private Entries entries1;

    @Before
    public void setUp() {
        HashMap<Account, ArrayList<Transaction>> hashMap = new HashMap<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactionManager = new TransactionManager(hashMap, transactions);
        account1 = new Account(1, transactionManager);
        account2 = new Account(2, transactionManager);
        transaction1 = transactionManager.createTransaction(333, account1, account2);
        transaction2 = transactionManager.createTransaction(-777, account1, account2);
        transaction3 = transactionManager.createTransaction(-222, account1, account2);
        transaction4 = transactionManager.createTransaction(888, account2, account1);
        localDateTime1 = LocalDateTime.now();
        localDateTime2 = LocalDateTime.now().plusMinutes(1);
        localDateTime3 = LocalDateTime.now().plusDays(5);
        localDateTime4 = LocalDateTime.now().plusDays(15);
        entry1 = new Entry(account1, transaction1, transaction1.getAmount(), localDateTime1);
        entry2 = new Entry(account1, transaction2, transaction2.getAmount(), localDateTime2);
        entry3 = new Entry(account1, transaction3, transaction3.getAmount(), localDateTime3);
        entry4 = new Entry(account1, transaction4, transaction4.getAmount(), localDateTime4);
        entries1 = account1.getEntries();
    }

    @Test
    public void addEntry() {
        entries1.addEntry(entry1);
        assertEquals(entry1.getTime(), entries1.last().getTime());
        entries1.addEntry(entry2);
        assertEquals(entry2.getTime(), entries1.last().getTime());
    }

    @Test
    public void fromFromRandomTime() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        entries1.addEntry(entry4);
        ArrayList<Entry> expectedList = new ArrayList<>();
        expectedList.add(entry3);
        expectedList.add(entry4);
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.from(localDateTime2.plusDays(3).toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void fromWithNoElement() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        entries1.addEntry(entry4);
        ArrayList<Entry> expectedList = new ArrayList<>();
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.from(localDateTime2.plusDays(30).toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void fromAllElements() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        entries1.addEntry(entry4);
        ArrayList<Entry> expectedList = new ArrayList<>();
        expectedList.add(entry1);
        expectedList.add(entry2);
        expectedList.add(entry3);
        expectedList.add(entry4);
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.from(localDateTime1.minusDays(30).toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void fromFromFirstElement() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        ArrayList<Entry> expectedList = new ArrayList<>();
        expectedList.add(entry1);
        expectedList.add(entry2);
        expectedList.add(entry3);
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.from(localDateTime1.toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenDatesAllEntries() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        entries1.addEntry(entry4);
        ArrayList<Entry> expectedList = new ArrayList<>();
        expectedList.add(entry1);
        expectedList.add(entry2);
        expectedList.add(entry3);
        expectedList.add(entry4);
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.betweenDates(localDateTime1.minusDays(3).toLocalDate(), localDateTime4.plusDays(100).toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenDatesOneEntries() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        entries1.addEntry(entry4);
        ArrayList<Entry> expectedList = new ArrayList<>();
        expectedList.add(entry3);
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.betweenDates(localDateTime2.plusDays(1).toLocalDate(), localDateTime3.plusDays(1).toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenDatesSomeEntries() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        entries1.addEntry(entry4);
        ArrayList<Entry> expectedList = new ArrayList<>();
        expectedList.add(entry1);
        expectedList.add(entry2);
        expectedList.add(entry3);
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.betweenDates(localDateTime1.toLocalDate(), localDateTime3.plusDays(1).toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void betweenWithNoEntries() {
        entries1.addEntry(entry1);
        entries1.addEntry(entry2);
        entries1.addEntry(entry3);
        entries1.addEntry(entry4);
        ArrayList<Entry> expectedList = new ArrayList<>();
        ArrayList<Entry> entriesList = (ArrayList<Entry>) entries1.betweenDates(localDateTime3.plusDays(1).toLocalDate(), localDateTime4.minusDays(1).toLocalDate());
        Assert.assertArrayEquals(expectedList.toArray(), entriesList.toArray());
    }

    @Test
    public void last() {
        entries1.addEntry(entry1);
        Assert.assertEquals(entry1, entries1.last());
        entries1.addEntry(entry2);
        Assert.assertEquals(entry2, entries1.last());
        entries1.addEntry(entry3);
        Assert.assertEquals(entry3, entries1.last());
        entries1.addEntry(entry4);
        Assert.assertEquals(entry4, entries1.last());
    }

    @Test
    public void first() {
        entries1.addEntry(entry1);
        Assert.assertEquals(entry1, entries1.first());
        entries1.addEntry(entry2);
        Assert.assertEquals(entry1, entries1.first());
        entries1.addEntry(entry3);
        Assert.assertEquals(entry1, entries1.first());
        entries1.addEntry(entry4);
        Assert.assertEquals(entry1, entries1.first());
    }
}