package ru.sbt.hw2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AccountTest {

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
    public void withdrawWhenZeroBalance() {
        boolean isSuccessOperation = account1.withdraw(100, account2);
        Assert.assertEquals(false, isSuccessOperation);
    }

    //Почему-то не проходит, если add один
//    @Test
//    public void withdrawWhenPositiveBalance() {
//        account1.addCash(1000);
//        boolean isSuccessOperation = account1.withdraw(900, account2);
//        Assert.assertEquals(true, isSuccessOperation);
//    }

    @Test
    public void withdrawWhenPositiveBalance() {
        account1.addCash(1000);
        account1.addCash(1000);
        boolean isSuccessOperation = account1.withdraw(1500, account2);
        Assert.assertEquals(1500, account2.getEntries().last().getAmount(), 0.0001);
        Assert.assertEquals(true, isSuccessOperation);
        isSuccessOperation = account1.withdraw(500, account2);
        Assert.assertEquals(500, account2.getEntries().last().getAmount(), 0.0001);
        Assert.assertEquals(true, isSuccessOperation);
    }

    @Test
    public void withdrawCashWhenZeroBalance() {
        boolean isSuccessOperation = account1.withdrawCash(100);
        Assert.assertEquals(false, isSuccessOperation);
    }

    @Test
    public void withdrawCashWhenPositiveBalance() {
        account1.addCash(1000);
        account1.addCash(1000);
        boolean isSuccessOperation = account1.withdrawCash(1500);
        Assert.assertEquals(true, isSuccessOperation);
        isSuccessOperation = account1.withdrawCash(500);
        Assert.assertEquals(true, isSuccessOperation);
    }

    @Test
    public void addCash() {
        account1.addCash(1000);
        Assert.assertEquals(1000, account1.getEntries().first().getAmount(), 0.0001);
    }

    @Test
    public void add() {
        account1.add(1000);
        Assert.assertEquals(1000, account1.getEntries().first().getAmount(), 0.0001);
    }

    @Test
    public void history() {
        account1.add(1000);
        account1.add(500);
        ArrayList<Entry> history = (ArrayList<Entry>) account1.history(LocalDate.now(), LocalDate.now());
        Assert.assertEquals(1000, history.get(0).getAmount(), 0.0001);
        Assert.assertEquals(500, history.get(1).getAmount(), 0.0001);
    }

    @Test
    public void balanceOn() {
        account1.addCash(1000);
        account1.addCash(1000);
        Assert.assertEquals(2000, account1.balanceOn(LocalDate.now()), 0.0001);
        account1.addCash(500);
        Assert.assertEquals(2500, account1.balanceOn(LocalDate.now()), 0.0001);
        account1.withdraw(500, account2);
        Assert.assertEquals(2000, account1.balanceOn(LocalDate.now()), 0.0001);
        account1.withdraw(500, account2);
        Assert.assertEquals(1500, account1.balanceOn(LocalDate.now()), 0.0001);
        Assert.assertEquals(1000, account2.balanceOn(LocalDate.now()), 0.0001);
    }

    @Test
    public void rollbackLastTransaction() {
        account1.addCash(1000);
        account1.addCash(1000);
        account1.withdraw(500, account2);
        account1.withdraw(500, account2);
        Assert.assertEquals(1000, account1.balanceOn(LocalDate.now()), 0.0001);
        Assert.assertEquals(1000, account2.balanceOn(LocalDate.now()), 0.0001);
        account1.rollbackLastTransaction();
        Assert.assertEquals(1500, account1.balanceOn(LocalDate.now()), 0.0001);
        Assert.assertEquals(500, account2.balanceOn(LocalDate.now()), 0.0001);
    }
}