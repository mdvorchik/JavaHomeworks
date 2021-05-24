package ru.sbt.mipt.homework.hw2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionTest {

    private TransactionManager transactionManager;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Account account1;
    private Account account2;

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

    }

    @Test (expected = IllegalStateException.class)
    public void executeWithException() {
        transaction2 = transaction1.execute();
        transaction2.execute();
    }

    @Test
    public void executeWorkByAmount() {
        transaction1.execute();
        Assert.assertEquals( -333, account1.getEntries().last().getAmount(), 0.0001);
        Assert.assertEquals( 333, account2.getEntries().last().getAmount(),0.0001);
    }

    @Test (expected = IllegalStateException.class)
    public void rollbackWithExceptionTooManyRollback() {
        transaction2 = transaction1.execute();
        transaction3 = transaction2.rollback();
        transaction3.rollback();
    }

    @Test (expected = IllegalStateException.class)
    public void rollbackWithExceptionTransactionDoesNotExecutedYet() {
        transaction1.rollback();
    }

    @Test
    public void rollbackWorkByAmount() {
        transaction2 = transaction1.execute();
        transaction2.rollback();
        Assert.assertEquals( 333, account1.getEntries().last().getAmount(), 0.0001);
        Assert.assertEquals( -333, account2.getEntries().last().getAmount(), 0.0001);
    }
}