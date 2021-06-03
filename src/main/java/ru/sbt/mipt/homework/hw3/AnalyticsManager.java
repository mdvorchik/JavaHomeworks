package ru.sbt.mipt.homework.hw3;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;
    private static final Comparator<Transaction> transactionComparatorByAmount =
            (Transaction t1, Transaction t2) -> Double.compare(t2.getAmount(), t1.getAmount());

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account debitCard) {
        Integer max = 0;
        Account mostFrequentBeneficiaryOfDebitCard = null;
        Map<Account, Integer> mapBeneficiaryToFrequency = new HashMap<>();
        Collection<Transaction> transactionCollection = transactionManager.findAllTransactionsByAccount(debitCard);
        for (Transaction transaction : transactionCollection) {
            mapBeneficiaryToFrequency.putIfAbsent(transaction.getBeneficiary(), 1);
            Integer tempInteger = mapBeneficiaryToFrequency.computeIfPresent(transaction.getBeneficiary(),
                    (key, value) -> value + 1);
            if (max < tempInteger) {
                max = tempInteger;
                mostFrequentBeneficiaryOfDebitCard = transaction.getBeneficiary();
            }
        }
        return mostFrequentBeneficiaryOfDebitCard;
    }

    public Collection<Transaction> topTenExpensivePurchases(DebitCard debitCard) {
        List<Transaction> transactionCollection = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(debitCard);
        transactionCollection.sort(transactionComparatorByAmount);
        List<Transaction> topTenExpensivePurchases = new ArrayList<>(transactionCollection.subList(0, 10));
        return topTenExpensivePurchases;
    }
}
