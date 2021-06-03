package ru.sbt.mipt.homework.hw3;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeTrue;

public class AccountTest {

    @Test
    public void withdrawWhenZeroBalance() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        //verify
        assertFalse(accountList.get(0).withdraw(100, accountList.get(1)));
    }

    @Test
    public void withdrawWhenPositiveBalance() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        assumeTrue(accountList.get(0).addCash(1000));
        //when
        assumeTrue(accountList.get(0).withdraw(700, accountList.get(1)));
        //verify
        assertEquals(300, accountList.get(0).balanceOn(LocalDate.now()), 0.0001); //300 = 1000 - 700
        assertEquals(700, accountList.get(1).balanceOn(LocalDate.now()), 0.0001); //700 = 0 + 700
    }

    @Test
    public void withdrawCashWhenZeroBalance() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        //verify
        assertFalse(accountList.get(0).withdrawCash(100));
    }

    @Test
    public void withdrawCashWhenPositiveBalance() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        assumeTrue(accountList.get(0).addCash(2000));
        //when
        assumeTrue(accountList.get(0).withdrawCash(1500));
        //verify
        assertEquals(500, accountList.get(0).balanceOn(LocalDate.now()), 0.0001); //500 = 2000 - 1500
    }

    @Test
    public void addCash() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        //when
        assumeTrue(accountList.get(0).addCash(1000));
        //verify
        assertEquals(1000, accountList.get(0).getEntries().first().getAmount(), 0.0001);
    }

    @Test
    public void add() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        //when
        assumeTrue(accountList.get(0).add(1000));
        //verify
        assertEquals(1000, accountList.get(0).getEntries().first().getAmount(), 0.0001);
    }

    @Test
    public void history() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        assumeTrue(accountList.get(0).add(1000));
        assumeTrue(accountList.get(0).add(500));
        //when
        List<Entry> history = (ArrayList<Entry>) accountList.get(0).history(LocalDate.now(), LocalDate.now());
        //verify
        assertEquals(1000, history.get(0).getAmount(), 0.0001);
        assertEquals(500, history.get(1).getAmount(), 0.0001);
    }

    @Test
    public void balanceOn() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        assumeTrue(accountList.get(0).addCash(2000));
        //verify
        assertEquals(2000, accountList.get(0).balanceOn(LocalDate.now()), 0.0001);
    }

    @Test
    public void rollbackLastTransaction() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        assumeTrue(accountList.get(0).addCash(2000));
        //when
        accountList.get(0).rollbackLastTransaction();
        //verify
        assertEquals(0, accountList.get(0).balanceOn(LocalDate.now()), 0.0001);
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
}