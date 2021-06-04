package ru.sbt.mipt.homework.hw4;

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
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        //when
        Transaction transaction = transactionManager.createTransaction(333, debitCardList.get(0), debitCardList.get(1));
        assumeTrue(transaction.getOriginator().equals(debitCardList.get(0)));
        assumeTrue(transaction.getBeneficiary().equals(debitCardList.get(1)));
        //verify
        assertEquals(333, transaction.getAmount(), 0.0001);
    }

    @Test
    public void findAllTransactionsByAccount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(333, debitCardList.get(0), debitCardList.get(1));
        Transaction transaction2 = transactionManager.createTransaction(-777, debitCardList.get(0), debitCardList.get(1));
        List<Transaction> transactionExpected = new ArrayList<>(Arrays.asList(transaction1, transaction2));
        //verify
        Assert.assertArrayEquals(transactionExpected.toArray(), transactionManager.findAllTransactionsByAccount(debitCardList.get(0)).toArray());
    }

    @Test
    public void rollbackTransaction() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        Transaction transaction = transactionManager.createTransaction(333, debitCardList.get(0), debitCardList.get(1));
        transactionManager.executeTransaction(transaction);
        List<Transaction> transactions = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(debitCardList.get(0));
        Transaction executedTransaction = transactions.get(transactions.size() - 1);
        //when
        assumeTrue(debitCardList.get(0).getEntries().last().getAmount() == -333);
        executedTransaction.rollback();
        //verify
        assertEquals(333, debitCardList.get(0).getEntries().last().getAmount(), 0.0001);
    }

    @Test
    public void executeTransaction() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(333, debitCardList.get(0), debitCardList.get(1));
        //when
        transactionManager.executeTransaction(transaction1);
        //verify
        assertEquals(-333, debitCardList.get(0).getEntries().last().getAmount(), 0.0001);
    }

    private TransactionManager prepareTransactionManager() {
        return new TransactionManager(new HashMap<>(), new ArrayList<>());
    }

    private List<DebitCard> prepareAccountList(TransactionManager transactionManager) {
        List<DebitCard> debitCards = new ArrayList<>();
        debitCards.add(new DebitCard(1, transactionManager));
        debitCards.add(new DebitCard(2, transactionManager));
        return debitCards;
    }
}