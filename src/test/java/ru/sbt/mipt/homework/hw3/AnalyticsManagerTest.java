package ru.sbt.mipt.homework.hw3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AnalyticsManagerTest {

    private final Comparator<Transaction> transactionComparatorByAmount =
            (Transaction t1, Transaction t2) -> Double.compare(t2.getAmount(), t1.getAmount());

    /**
     * total count beneficiary by account1:
     * account2 - 4
     * account3 - 6
     * account4 - 7
     */
    @Test
    public void mostFrequentBeneficiaryOfAccount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        List<Account> accountList = prepareAccountList(transactionManager);
        fillAccountsByTrashTransactions(transactionManager, accountList);
        prepareTopTenExpensivePurchases(transactionManager, accountList);
        //verify
        assertEquals(accountList.get(3), analyticsManager.mostFrequentBeneficiaryOfAccount(accountList.get(0)));
    }

    @Test
    public void transactionComparatorByAmountTest() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accountList = prepareAccountList(transactionManager);
        fillAccountsByTrashTransactions(transactionManager, accountList);
        List<Transaction> transactionList = prepareTopTenExpensivePurchases(transactionManager, accountList);
        //verify
        assertEquals(transactionList.get(0).getAmount(), 1000000, 0.0001);
        assertEquals(transactionList.get(9).getAmount(), 7000, 0.0001);
    }

    @Test
    public void topTenExpensivePurchases() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        List<Account> accountList = prepareAccountList(transactionManager);
        fillAccountsByTrashTransactions(transactionManager, accountList);
        List<Transaction> transactionList = prepareTopTenExpensivePurchases(transactionManager, accountList);
        //verify
        assertArrayEquals(transactionList.toArray(), analyticsManager.topTenExpensivePurchases(accountList.get(0)).toArray());
    }

    private TransactionManager prepareTransactionManager() {
        return new TransactionManager(new HashMap<>(), new ArrayList<>());
    }

    private List<Account> prepareAccountList(TransactionManager transactionManager) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, transactionManager));
        accounts.add(new Account(2, transactionManager));
        accounts.add(new Account(3, transactionManager));
        accounts.add(new Account(4, transactionManager));
        return accounts;
    }

    private void fillAccountsByTrashTransactions(TransactionManager transactionManager, List<Account> accounts) {
        if (accounts.size() > 3) {
            transactionManager.createTransaction(1000, accounts.get(0), accounts.get(2));
            transactionManager.createTransaction(200, accounts.get(0), accounts.get(3));
            transactionManager.createTransaction(1700, accounts.get(0), accounts.get(2));
            transactionManager.createTransaction(1050, accounts.get(0), accounts.get(2));
            transactionManager.createTransaction(1300, accounts.get(0), accounts.get(3));
            transactionManager.createTransaction(1700, accounts.get(0), accounts.get(2));
            transactionManager.createTransaction(4000, accounts.get(0), accounts.get(1));
        }
    }

    private List<Transaction> prepareTopTenExpensivePurchases(TransactionManager transactionManager, List<Account> accounts) {
        List<Transaction> transactions = new ArrayList<>();
        if (accounts.size() > 3) {
            transactions.add(transactionManager.createTransaction(15000, accounts.get(0), accounts.get(2)));
            transactions.add(transactionManager.createTransaction(20000, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(30000, accounts.get(0), accounts.get(3)));
            transactions.add(transactionManager.createTransaction(30000, accounts.get(0), accounts.get(3)));
            transactions.add(transactionManager.createTransaction(10000, accounts.get(0), accounts.get(3)));
            transactions.add(transactionManager.createTransaction(9000, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(9000, accounts.get(0), accounts.get(2)));
            transactions.add(transactionManager.createTransaction(7000, accounts.get(0), accounts.get(3)));
            transactions.add(transactionManager.createTransaction(100000, accounts.get(0), accounts.get(1)));
            transactions.add(transactionManager.createTransaction(1000000, accounts.get(0), accounts.get(3)));
            transactions.sort(transactionComparatorByAmount);
        }
        return transactions;
    }
}