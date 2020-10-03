package ru.sbt.hw2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AnalyticsManagerTest {

    private TransactionManager transactionManager;
    private AnalyticsManager analyticsManager;
    private Transaction transaction;
    private ArrayList<Transaction> expectedTopTenExpensivePurchases;
    private Account account1;
    private Account account2;
    private Account account3;
    private Account account4;

    private Comparator<Transaction> transactionComparatorByAmount =
            (Transaction t1, Transaction t2)->Double.compare(t2.getAmount(), t1.getAmount());

    @Before
    public void setUp() {
        HashMap<Account, ArrayList<Transaction>> hashMap = new HashMap<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactionManager = new TransactionManager(hashMap, transactions);
        analyticsManager = new AnalyticsManager(transactionManager);
        expectedTopTenExpensivePurchases = new ArrayList<>();
        account1 = new Account(1, transactionManager);
        account2 = new Account(2, transactionManager);
        account3 = new Account(3, transactionManager);
        account4 = new Account(4, transactionManager);
        transaction = transactionManager.createTransaction(1000, account1, account3);
        transaction = transactionManager.createTransaction(15000, account1, account3);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(200, account1, account4);
        transaction = transactionManager.createTransaction(20000, account1, account2);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(1700, account1, account3);
        transaction = transactionManager.createTransaction(30000, account1, account4);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(30000, account1, account4);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(10000, account1, account4);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(9000, account1, account2);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(9000, account1, account3);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(7000, account1, account4);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(100000, account1, account2);
        expectedTopTenExpensivePurchases.add(transaction);
        transaction = transactionManager.createTransaction(1050, account1, account3);
        transaction = transactionManager.createTransaction(1300, account1, account4);
        transaction = transactionManager.createTransaction(1700, account1, account3);
        transaction = transactionManager.createTransaction(4000, account1, account2);
        transaction = transactionManager.createTransaction(1000000, account1, account4);
        expectedTopTenExpensivePurchases.add(transaction);
        expectedTopTenExpensivePurchases.sort(transactionComparatorByAmount);
    }

    /**
     * total count beneficiary by account1:
     *      account2 - 4
     *      account3 - 6
     *      account4 - 7
     */
    @Test
    public void mostFrequentBeneficiaryOfAccount() {
        Assert.assertEquals(account4, analyticsManager.mostFrequentBeneficiaryOfAccount(account1));
    }

    @Test
    public void transactionComparatorByAmountTest() {
        Assert.assertEquals(expectedTopTenExpensivePurchases.get(0).getAmount(), 1000000, 0.0001);
        Assert.assertEquals(expectedTopTenExpensivePurchases.get(9).getAmount(), 7000, 0.0001);
    }

    @Test
    public void topTenExpensivePurchases() {
        Assert.assertArrayEquals(expectedTopTenExpensivePurchases.toArray(), analyticsManager.topTenExpensivePurchases(account1).toArray());

    }
}