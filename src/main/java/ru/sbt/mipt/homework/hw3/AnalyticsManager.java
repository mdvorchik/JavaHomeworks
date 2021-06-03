package ru.sbt.mipt.homework.hw3;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;
    private static final Comparator<Transaction> transactionComparatorByAmount =
            (Transaction t1, Transaction t2) -> Double.compare(t2.getAmount(), t1.getAmount());

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Integer max = 0;
        Account mostFrequentBeneficiaryOfAccount = null;
        Map<Account, Integer> mapBeneficiaryToFrequency = new HashMap<>();
        Collection<Transaction> transactionCollection = transactionManager.findAllTransactionsByAccount(account);
        for (Transaction transaction: transactionCollection) {
            mapBeneficiaryToFrequency.putIfAbsent(transaction.getBeneficiary(), 1);
            Integer tempInteger = mapBeneficiaryToFrequency.computeIfPresent(transaction.getBeneficiary(),
                    (key, value) -> value + 1);
            if (max < tempInteger) {
                max = tempInteger;
                mostFrequentBeneficiaryOfAccount = transaction.getBeneficiary();
            }
        }
        return mostFrequentBeneficiaryOfAccount;
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        List<Transaction> transactionCollection = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(account);
        transactionCollection.sort(transactionComparatorByAmount);
        List<Transaction> topTenExpensivePurchases = new ArrayList<>(transactionCollection.subList(0, 10));
        return topTenExpensivePurchases;
    }
}
