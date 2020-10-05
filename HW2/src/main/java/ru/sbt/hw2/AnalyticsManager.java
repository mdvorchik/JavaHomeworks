package ru.sbt.hw2;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;
    private Comparator<Transaction> transactionComparatorByAmount =
            (Transaction t1, Transaction t2)->Double.compare(t2.getAmount(), t1.getAmount());

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Integer max = 0;
        Account mostFrequentBeneficiaryOfAccount = null;
        Map<Account, Integer> mapBeneficiaryToFrequency = new HashMap<>();
        Collection<Transaction> transactionCollection = transactionManager.findAllTransactionsByAccount(account);
        for (Transaction transaction: transactionCollection) {
            if (mapBeneficiaryToFrequency.containsKey(transaction.getBeneficiary())){
                Integer tempInteger = mapBeneficiaryToFrequency.get(transaction.getBeneficiary()) + 1;
                mapBeneficiaryToFrequency.put(transaction.getBeneficiary(), tempInteger);
                if (max < tempInteger) {
                    max = tempInteger;
                    mostFrequentBeneficiaryOfAccount = transaction.getBeneficiary();
                }
            } else {
                mapBeneficiaryToFrequency.put(transaction.getBeneficiary(), 1);
                if (max < 1) {
                    max = 1;
                    mostFrequentBeneficiaryOfAccount = transaction.getBeneficiary();
                }
            }
        }
        return mostFrequentBeneficiaryOfAccount;
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        ArrayList<Transaction> transactionCollection = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(account);
        transactionCollection.sort(transactionComparatorByAmount);
        ArrayList<Transaction> topTenExpensivePurchases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            topTenExpensivePurchases.add(i, transactionCollection.get(i));
        }
        return topTenExpensivePurchases;
    }
}
