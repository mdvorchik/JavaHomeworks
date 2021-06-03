package ru.sbt.mipt.homework.hw3;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

public class TransactionManagerTest {

    @Test
    public void createTransaction() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        //when
        Transaction transaction = transactionManager.createTransaction(333, accountList.get(0), accountList.get(1));
        assumeTrue(transaction.getOriginator().equals(accountList.get(0)));
        assumeTrue(transaction.getBeneficiary().equals(accountList.get(1)));
        //verify
        assertEquals(333, transaction.getAmount(), 0.0001);
    }

    @Test
    public void findAllTransactionsByAccount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(333, accountList.get(0), accountList.get(1));
        Transaction transaction2 = transactionManager.createTransaction(-777, accountList.get(0), accountList.get(1));
        List<Transaction> transactionExpected = new ArrayList<>(Arrays.asList(transaction1, transaction2));
        //verify
        Assert.assertArrayEquals(transactionExpected.toArray(), transactionManager.findAllTransactionsByAccount(accountList.get(0)).toArray());
    }

    @Test
    public void rollbackTransaction() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        Transaction transaction = transactionManager.createTransaction(333, accountList.get(0), accountList.get(1));
        transactionManager.executeTransaction(transaction);
        List<Transaction> transactions = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(accountList.get(0));
        Transaction executedTransaction = transactions.get(transactions.size() - 1);
        //when
        assumeTrue(accountList.get(0).getEntries().last().getAmount() == -333);
        executedTransaction.rollback();
        //verify
        assertEquals(333, accountList.get(0).getEntries().last().getAmount(), 0.0001);
    }

    @Test
    public void executeTransaction() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(333, accountList.get(0), accountList.get(1));
        //when
        transactionManager.executeTransaction(transaction1);
        //verify
        assertEquals(-333, accountList.get(0).getEntries().last().getAmount(), 0.0001);
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