package ru.sbt.hw2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TransactionManager {
    private HashMap<Account, ArrayList<Transaction>> transactionsByAccountMap;
    private ArrayList<Transaction> transactions;

    private void addTransactionToAccount(Transaction transaction, Account account) {
        if (!transactionsByAccountMap.containsKey(account)) {
            ArrayList<Transaction> transactionArrayList = new ArrayList<>(1);
            transactionArrayList.add(transaction);
            transactionsByAccountMap.put(account, transactionArrayList);
        }
        transactionsByAccountMap.get(account).add(transaction);
    }

    public TransactionManager(HashMap<Account, ArrayList<Transaction>> transactionsByAccountMap, ArrayList<Transaction> transactions) {
        this.transactionsByAccountMap = transactionsByAccountMap;
        this.transactions = transactions;
    }


    /**
     * Creates and stores transactions
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        if (originator == null || beneficiary == null) throw new IllegalArgumentException("originator and beneficiary must not be null");
        Transaction transaction = new Transaction(transactions.size(), amount, originator, beneficiary);
        transactions.add(transaction);
        addTransactionToAccount(transaction, originator);
        addTransactionToAccount(transaction, beneficiary);
        return transaction;
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        if (account == null) throw new IllegalArgumentException();
        ArrayList<Transaction> transactionsByAccount = transactionsByAccountMap.get(account);
        return transactionsByAccount;
    }


    public void rollbackTransaction(Transaction transaction) {
        // write your code here
    }

    public void executeTransaction(Transaction transaction) {
        // write your code here
    }
}
