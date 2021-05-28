package ru.sbt.mipt.homework.hw2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountTest {

    private TransactionManager transactionManager;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() {
        HashMap<Account, List<Transaction>> hashMap = new HashMap<>();
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

    @Test
    public void withdrawWhenPositiveBalance() {
        account1.addCash(1000);
        boolean isSuccessOperation = account1.withdraw(700, account2);
        Assert.assertEquals(true, isSuccessOperation);
        Assert.assertEquals(300, account1.balanceOn(LocalDate.now()), 0.0001); //300 = 1000 - 700
        Assert.assertEquals(700, account2.balanceOn(LocalDate.now()), 0.0001); //700 = 0 + 700
        isSuccessOperation = account2.withdraw(100, account1);
        Assert.assertEquals(true, isSuccessOperation);
        Assert.assertEquals(400, account1.balanceOn(LocalDate.now()), 0.0001); //400 = 300 + 100
        Assert.assertEquals(600, account2.balanceOn(LocalDate.now()), 0.0001); //600 = 700 - 100
    }

    @Test
    public void withdrawCashWhenZeroBalance() {
        boolean isSuccessOperation = account1.withdrawCash(100);
        Assert.assertEquals(false, isSuccessOperation);
    }

    @Test
    public void withdrawCashWhenPositiveBalance() {
        account1.addCash(2000);
        boolean isSuccessOperation = account1.withdrawCash(1500);
        Assert.assertEquals(true, isSuccessOperation);
        Assert.assertEquals(500, account1.balanceOn(LocalDate.now()), 0.0001); //500 = 2000 - 1500
        isSuccessOperation = account1.withdrawCash(500);
        Assert.assertEquals(true, isSuccessOperation);
        Assert.assertEquals(0, account1.balanceOn(LocalDate.now()), 0.0001);   //0 = 500 - 500
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
        account1.addCash(2000);
        Assert.assertEquals(2000, account1.balanceOn(LocalDate.now()), 0.0001);
        account1.addCash(500);
        Assert.assertEquals(2500, account1.balanceOn(LocalDate.now()), 0.0001); //2500 = 2000 + 500
        account1.withdraw(500, account2);
        Assert.assertEquals(2000, account1.balanceOn(LocalDate.now()), 0.0001); //2000 = 2500 - 500
        Assert.assertEquals(500, account2.balanceOn(LocalDate.now()), 0.0001);  //500 = 0 + 500
        account1.withdraw(500, account2);
        Assert.assertEquals(1500, account1.balanceOn(LocalDate.now()), 0.0001); //1500 = 2000 - 500
        Assert.assertEquals(1000, account2.balanceOn(LocalDate.now()), 0.0001); //1000 = 500 + 500
    }

    @Test
    public void rollbackLastTransaction() {
        account1.addCash(2000);
        account1.withdraw(700, account2);
        account1.withdraw(300, account2);
        Assert.assertEquals(1000, account1.balanceOn(LocalDate.now()), 0.0001); //1000 = 2000 - 700 - 300
        Assert.assertEquals(1000, account2.balanceOn(LocalDate.now()), 0.0001); //1000 = 700 + 300
        account1.rollbackLastTransaction();
        Assert.assertEquals(1300, account1.balanceOn(LocalDate.now()), 0.0001); //1300 = 1000 - (-300)
        Assert.assertEquals(700, account2.balanceOn(LocalDate.now()), 0.0001);  //700 = 1000 - (+300)
    }
}