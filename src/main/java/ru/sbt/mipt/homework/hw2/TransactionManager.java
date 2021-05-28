package ru.sbt.mipt.homework.hw2;

import java.util.*;

public class TransactionManager {
    private final Map<Account, List<Transaction>> transactionsByAccount;
    private final ArrayList<Transaction> transactions;

    private void addTransactionToAccount(Transaction transaction, Account account) {
        transactionsByAccount.computeIfPresent(account, (key, value) -> {
            value.add(transaction);
            return value;
        });
        transactionsByAccount.putIfAbsent(account, new ArrayList<>(Collections.singletonList(transaction)));
    }

    private void addTransactionToAllPlaces(Transaction transaction, Account originator, Account beneficiary) {
        transactions.add(transaction);
        if (originator != null) addTransactionToAccount(transaction, originator);
        if (beneficiary != null) addTransactionToAccount(transaction, beneficiary);
    }

    public TransactionManager(HashMap<Account, List<Transaction>> transactionsByAccountMap, ArrayList<Transaction> transactions) {
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

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        if (account == null) throw new IllegalArgumentException();
        return this.transactionsByAccount.get(account);
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
