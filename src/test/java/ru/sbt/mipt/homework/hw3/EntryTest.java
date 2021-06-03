package ru.sbt.mipt.homework.hw3;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class EntryTest {

    @Test
    public void constructorOfEntryTest() {
        //given
        TransactionManager transactionManager = new TransactionManager(new HashMap<>(), new ArrayList<>());
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        Transaction transaction = transactionManager.createTransaction(333, account1, account2);
        LocalDateTime localDateTime = LocalDateTime.now();
        //when
        Entry entry = new Entry(account1, transaction, transaction.getAmount(), localDateTime);
        Assume.assumeTrue(localDateTime.equals(entry.getTime()));
        //verify
        Assert.assertEquals(transaction.getAmount(), entry.getAmount(), 0.0001);
    }
}