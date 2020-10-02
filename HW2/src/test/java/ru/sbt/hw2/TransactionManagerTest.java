package ru.sbt.hw2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TransactionManagerTest {

    private TransactionManager transactionManager;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() {
        HashMap<Account, ArrayList<Transaction>> hashMap = new HashMap<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactionManager = new TransactionManager(hashMap, transactions);
        account1 = new Account(1, transactionManager);
        account2 = new Account(2, transactionManager);

    }

    @Test
    public void createTransaction() {
        transaction1 = transactionManager.createTransaction(333, account1, account2);
        transactionManager.executeTransaction(transaction1);
        Assert.assertEquals( -333, account1.getEntries().last().getAmount(), 0.0001);
        Assert.assertEquals( 333, account2.getEntries().last().getAmount(),0.0001);
    }

    @Test
    public void findAllTransactionsByAccount() {
        ArrayList<Transaction> transactionExpected = new ArrayList<>();
        transaction1 = transactionManager.createTransaction(333, account1, account2);
        transactionExpected.add(transaction1);
        Assert.assertArrayEquals(transactionExpected.toArray(), transactionManager.findAllTransactionsByAccount(account1).toArray());
        transaction2 = transactionManager.createTransaction(-777, account1, account2);
        transactionExpected.add(transaction2);
        Assert.assertArrayEquals(transactionExpected.toArray(), transactionManager.findAllTransactionsByAccount(account1).toArray());
        transactionManager.executeTransaction(transaction2);
        Assert.assertEquals(3, transactionManager.findAllTransactionsByAccount(account1).size());

    }

    @Test
    public void rollbackTransaction() {
        transaction1 = transactionManager.createTransaction(333, account1, account2);
        transactionManager.executeTransaction(transaction1);
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(account1);
        transaction2 = transactions.get(transactions.size() - 1);
        transactionManager.rollbackTransaction(transaction2);
        Assert.assertEquals(3, transactionManager.findAllTransactionsByAccount(account1).size());
        Assert.assertEquals(3, transactionManager.findAllTransactionsByAccount(account2).size());
        Assert.assertEquals(333, account1.getEntries().last().getAmount(), 0.0001);
        Assert.assertEquals(-333, account2.getEntries().last().getAmount(), 0.0001);
    }

    @Test
    public void executeTransaction() {
        transaction1 = transactionManager.createTransaction(333, account1, account2);
        transactionManager.executeTransaction(transaction1);
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(account1);
        transaction2 = transactions.get(transactions.size() - 1);
        Assert.assertEquals(2, transactionManager.findAllTransactionsByAccount(account1).size());
        Assert.assertEquals(2, transactionManager.findAllTransactionsByAccount(account2).size());
        Assert.assertEquals(-333, account1.getEntries().last().getAmount(), 0.0001);
        Assert.assertEquals(333, account2.getEntries().last().getAmount(), 0.0001);
    }
}