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

    private void addTransactionToAllPlaces(Transaction transaction, Account originator, Account beneficiary) {
        transactions.add(transaction);
        if (originator != null) addTransactionToAccount(transaction, originator);
        if (beneficiary != null) addTransactionToAccount(transaction, beneficiary);
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
        Transaction transaction = new Transaction(transactions.size(), amount, originator, beneficiary, false, false);
        addTransactionToAllPlaces(transaction, originator, beneficiary);
        return transaction;
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        if (account == null) throw new IllegalArgumentException();
        ArrayList<Transaction> transactionsByAccount = transactionsByAccountMap.get(account);
        return transactionsByAccount;
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
