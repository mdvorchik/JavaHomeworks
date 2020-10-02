package ru.sbt.hw2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

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
        // write your code here
    }
}
