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
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        fillAccountsByTrashTransactions(transactionManager, debitCardList);
        prepareTopTenExpensivePurchases(transactionManager, debitCardList);
        //verify
        assertEquals(debitCardList.get(3), analyticsManager.mostFrequentBeneficiaryOfAccount(debitCardList.get(0)));
    }

    @Test
    public void transactionComparatorByAmountTest() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        fillAccountsByTrashTransactions(transactionManager, debitCardList);
        List<Transaction> transactionList = prepareTopTenExpensivePurchases(transactionManager, debitCardList);
        //verify
        assertEquals(transactionList.get(0).getAmount(), 1000000, 0.0001);
        assertEquals(transactionList.get(9).getAmount(), 7000, 0.0001);
    }

    @Test
    public void topTenExpensivePurchases() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        fillAccountsByTrashTransactions(transactionManager, debitCardList);
        List<Transaction> transactionList = prepareTopTenExpensivePurchases(transactionManager, debitCardList);
        //verify
        assertArrayEquals(transactionList.toArray(), analyticsManager.topTenExpensivePurchases(debitCardList.get(0)).toArray());
    }

    private TransactionManager prepareTransactionManager() {
        return new TransactionManager(new HashMap<>(), new ArrayList<>());
    }

    private List<DebitCard> prepareAccountList(TransactionManager transactionManager) {
        List<DebitCard> debitCards = new ArrayList<>();
        debitCards.add(new DebitCard(1, transactionManager));
        debitCards.add(new DebitCard(2, transactionManager));
        debitCards.add(new DebitCard(3, transactionManager));
        debitCards.add(new DebitCard(4, transactionManager));
        return debitCards;
    }

    private void fillAccountsByTrashTransactions(TransactionManager transactionManager, List<DebitCard> debitCards) {
        if (debitCards.size() > 3) {
            transactionManager.createTransaction(1000, debitCards.get(0), debitCards.get(2));
            transactionManager.createTransaction(200, debitCards.get(0), debitCards.get(3));
            transactionManager.createTransaction(1700, debitCards.get(0), debitCards.get(2));
            transactionManager.createTransaction(1050, debitCards.get(0), debitCards.get(2));
            transactionManager.createTransaction(1300, debitCards.get(0), debitCards.get(3));
            transactionManager.createTransaction(1700, debitCards.get(0), debitCards.get(2));
            transactionManager.createTransaction(4000, debitCards.get(0), debitCards.get(1));
        }
    }

    private List<Transaction> prepareTopTenExpensivePurchases(TransactionManager transactionManager, List<DebitCard> debitCards) {
        List<Transaction> transactions = new ArrayList<>();
        if (debitCards.size() > 3) {
            transactions.add(transactionManager.createTransaction(15000, debitCards.get(0), debitCards.get(2)));
            transactions.add(transactionManager.createTransaction(20000, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(30000, debitCards.get(0), debitCards.get(3)));
            transactions.add(transactionManager.createTransaction(30000, debitCards.get(0), debitCards.get(3)));
            transactions.add(transactionManager.createTransaction(10000, debitCards.get(0), debitCards.get(3)));
            transactions.add(transactionManager.createTransaction(9000, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(9000, debitCards.get(0), debitCards.get(2)));
            transactions.add(transactionManager.createTransaction(7000, debitCards.get(0), debitCards.get(3)));
            transactions.add(transactionManager.createTransaction(100000, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(1000000, debitCards.get(0), debitCards.get(3)));
            transactions.sort(transactionComparatorByAmount);
        }
        return transactions;
    }
}