package ru.sbt.mipt.homework.hw2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TransactionTest {

    @Test
    public void executeWithIllegalStateExceptionWhenExecuteTransactionTwice() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, accountList);
        //verify
        assertThrows(IllegalStateException.class, () -> transactionList.get(0).execute().execute());
    }

    @Test
    public void executeWorkByAmount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, accountList);
        //when
        transactionList.get(0).execute();
        //verify
        assertEquals(-333, accountList.get(0).getEntries().last().getAmount(), 0.0001);
        assertEquals(333, accountList.get(1).getEntries().last().getAmount(), 0.0001);
    }

    @Test
    public void rollbackWithExceptionWhenRollbackTwice() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, accountList);
        //verify
        assertThrows(IllegalStateException.class, () -> transactionList.get(0).execute().rollback().rollback());
    }

    @Test
    public void rollbackWithExceptionTransactionDoesNotExecutedYet() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, accountList);
        //verify
        assertThrows(IllegalStateException.class, () -> transactionList.get(0).rollback());
    }

    @Test
    public void rollbackWorkByAmount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, accountList);
        //when
        transactionList.get(0).execute().rollback();
        //verify
        assertEquals(333, accountList.get(0).getEntries().last().getAmount(), 0.0001);
        assertEquals(-333, accountList.get(1).getEntries().last().getAmount(), 0.0001);
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

    private List<Transaction> prepareTransactionList(TransactionManager transactionManager, List<Account> accounts) {
        List<Transaction> transactions = new ArrayList<>();
        if (accounts.size() > 1) {
            transactions.add(transactionManager.createTransaction(333, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(-777, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(-222, accounts.get(0), accounts.get(1)));
        }
        return transactions;
    }
}