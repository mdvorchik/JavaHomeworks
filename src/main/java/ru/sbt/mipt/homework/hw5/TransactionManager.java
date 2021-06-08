package ru.sbt.mipt.homework.hw5;

import java.util.*;

public class TransactionManager {
    private final Map<Account, List<Transaction>> transactionsByAccount;
    private final List<Transaction> transactions;

    private void addTransactionToAccount(Transaction transaction, Account debitCard) {
        transactionsByAccount.computeIfPresent(debitCard, (key, value) -> {
            value.add(transaction);
            return value;
        });
        transactionsByAccount.putIfAbsent(debitCard, new ArrayList<>(Collections.singletonList(transaction)));
    }

    private void addTransactionToAllPlaces(Transaction transaction, Account originator, Account beneficiary) {
        transactions.add(transaction);
        if (originator != null) addTransactionToAccount(transaction, originator);
        if (beneficiary != null) addTransactionToAccount(transaction, beneficiary);
    }

    public TransactionManager(Map<Account, List<Transaction>> transactionsByAccountMap, List<Transaction> transactions) {
        this.transactionsByAccount = transactionsByAccountMap;
        this.transactions = transactions;
    }

    /**
     * Creates and stores transactions
     *
     * @param amount      - amount
     * @param originator  - originator
     * @param beneficiary - beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        Transaction transaction = new Transaction(transactions.size(), amount, originator, beneficiary, false, false);
        addTransactionToAllPlaces(transaction, originator, beneficiary);
        return transaction;
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account debitCard) {
        if (debitCard == null) throw new IllegalArgumentException();
        return this.transactionsByAccount.get(debitCard);
    }

    public void rollbackTransaction(Transaction transaction) {
        Transaction rollbackTransaction = transaction.rollback();
        addTransactionToAllPlaces(rollbackTransaction, rollbackTransaction.getOriginator(), rollbackTransaction.getBeneficiary());
    }

    public void executeTransaction(Transaction transaction) {
        Transaction execTransaction = transaction.execute();
        addTransactionToAllPlaces(execTransaction, execTransaction.getOriginator(), execTransaction.getBeneficiary());
    }
}
