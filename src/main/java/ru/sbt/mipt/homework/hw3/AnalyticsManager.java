package ru.sbt.mipt.homework.hw3;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class AnalyticsManager {
    private final TransactionManager transactionManager;
    private static final Comparator<Transaction> transactionComparatorByAmount =
            (Transaction t1, Transaction t2) -> Double.compare(t2.getAmount(), t1.getAmount());

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account debitCard) {
        int max = 0;
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
        return new ArrayList<>(transactionCollection.subList(0, 10));
    }

    public double overallBalanceOfAccounts(List<Account> accounts) {
        LocalDate localDate = LocalDate.now();
        return accounts.stream()
                .mapToDouble(account -> account.balanceOn(localDate))
                .reduce(Double::sum)
                .orElse(0);
    }

    public Set<Object> uniqueKeysOf(List<Account> accounts, KeyExtractor<Account> extractor) {
        return accounts.stream()
                .map(extractor::extract)
                .collect(toSet());
    }

    public List<Account> accountsRangeFrom(List<Account> accounts, Account minAccount, Comparator<Account> comparator) {
        return accounts.stream()
                .sorted(comparator)
                .filter(account -> comparator.compare(minAccount, account) <= 0)
                .collect(toList());
    }
}
