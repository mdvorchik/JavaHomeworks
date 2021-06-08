package ru.sbt.mipt.homework.hw5;

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
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        Transaction transaction = transactionManager.createTransaction(333, debitCard1, debitCard2);
        LocalDateTime localDateTime = LocalDateTime.now();
        //when
        Entry entry = new Entry(debitCard1, transaction, transaction.getAmount(), localDateTime);
        Assume.assumeTrue(localDateTime.equals(entry.getTime()));
        //verify
        Assert.assertEquals(transaction.getAmount(), entry.getAmount(), 0.0001);
    }
}