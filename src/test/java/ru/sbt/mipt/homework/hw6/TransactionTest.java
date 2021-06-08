package ru.sbt.mipt.homework.hw6;

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
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, debitCardList);
        //verify
        assertThrows(IllegalStateException.class, () -> transactionList.get(0).execute().execute());
    }

    @Test
    public void executeWorkByAmount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, debitCardList);
        //when
        transactionList.get(0).execute();
        //verify
        assertEquals(-333, debitCardList.get(0).getEntries().last().getAmount(), 0.0001);
        assertEquals(333, debitCardList.get(1).getEntries().last().getAmount(), 0.0001);
    }

    @Test
    public void rollbackWithExceptionWhenRollbackTwice() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, debitCardList);
        //verify
        assertThrows(IllegalStateException.class, () -> transactionList.get(0).execute().rollback().rollback());
    }

    @Test
    public void rollbackWithExceptionTransactionDoesNotExecutedYet() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, debitCardList);
        //verify
        assertThrows(IllegalStateException.class, () -> transactionList.get(0).rollback());
    }

    @Test
    public void rollbackWorkByAmount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<DebitCard> debitCardList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, debitCardList);
        //when
        transactionList.get(0).execute().rollback();
        //verify
        assertEquals(333, debitCardList.get(0).getEntries().last().getAmount(), 0.0001);
        assertEquals(-333, debitCardList.get(1).getEntries().last().getAmount(), 0.0001);
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

    private List<Transaction> prepareTransactionList(TransactionManager transactionManager, List<DebitCard> debitCards) {
        List<Transaction> transactions = new ArrayList<>();
        if (debitCards.size() > 1) {
            transactions.add(transactionManager.createTransaction(333, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(-777, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(-222, debitCards.get(0), debitCards.get(1)));
        }
        return transactions;
    }
}