package ru.sbt.hw2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class EntryTest {
    private TransactionManager transactionManager;
    private Transaction transaction;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() {
        HashMap<Account, ArrayList<Transaction>> hashMap = new HashMap<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactionManager = new TransactionManager(hashMap, transactions);
        account1 = new Account(1, transactionManager);
        account2 = new Account(2, transactionManager);
        transaction = transactionManager.createTransaction(333, account1, account2);
    }

    @Test
    public void constructorOfEntryTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Entry entry = new Entry(account1, transaction, transaction.getAmount(), localDateTime);
        Assert.assertEquals(localDateTime, entry.getTime());
        Assert.assertEquals(transaction.getAmount(), entry.getAmount(), 0.0001);
    }


}