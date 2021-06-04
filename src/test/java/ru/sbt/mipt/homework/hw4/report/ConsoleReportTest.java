package ru.sbt.mipt.homework.hw4.report;

import org.junit.Test;
import ru.sbt.mipt.homework.hw4.Account;
import ru.sbt.mipt.homework.hw4.DebitCard;
import ru.sbt.mipt.homework.hw4.Transaction;
import ru.sbt.mipt.homework.hw4.TransactionManager;

import java.util.*;

public class ConsoleReportTest {
    private final Comparator<Transaction> transactionComparatorByAmount =
            (Transaction t1, Transaction t2) -> Double.compare(t2.getAmount(), t1.getAmount());

    @Test
    public void writeTo() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        fillAccountsByTrashTransactions(transactionManager, accounts);
        List<Transaction> transactions = prepareTopTenExpensivePurchases(transactionManager, accounts);
        Map<String, String> fieldNameMap = new HashMap<>();
        fieldNameMap.put("id", "Id");
        ReportGenerator reportGenerator = new SimpleReportGenerator(Transaction.class, fieldNameMap);
        //when
        Report report = reportGenerator.generate(transactions);
        report.writeTo(System.out);
    }

    private TransactionManager prepareTransactionManager() {
        return new TransactionManager(new HashMap<>(), new ArrayList<>());
    }

    private List<Account> prepareAccountList(TransactionManager transactionManager) {
        List<Account> debitCards = new ArrayList<>();
        debitCards.add(new DebitCard(1, transactionManager));
        debitCards.add(new DebitCard(2, transactionManager));
        debitCards.add(new DebitCard(3, transactionManager));
        debitCards.add(new DebitCard(4, transactionManager));
        return debitCards;
    }

    private void fillAccountsByTrashTransactions(TransactionManager transactionManager, List<Account> debitCards) {
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

    private List<Transaction> prepareTopTenExpensivePurchases(TransactionManager transactionManager, List<Account> debitCards) {
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