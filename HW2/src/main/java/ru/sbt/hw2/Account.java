package ru.sbt.hw2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;

    Entries getEntries() {
        return entries;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionManager, entries);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(transactionManager, account.transactionManager) &&
                Objects.equals(entries, account.entries);
    }

    public Account(long id, TransactionManager transactionManager) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.entries = new Entries(new ArrayList<>());
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, Account beneficiary) {
        boolean isOperationSuccess = false;
        LocalDate localDate = LocalDate.now();
        if (amount > 0 && this.balanceOn(localDate) - amount >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, beneficiary);
            transactionManager.executeTransaction(transaction);
            isOperationSuccess = true;
        }
        return isOperationSuccess;
    }

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdrawCash(double amount) {
        boolean isOperationSuccess = withdraw(amount, null);
        return isOperationSuccess;
    }

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean addCash(double amount) {
        boolean isOperationSuccess = false;
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(amount, null, this);
            transactionManager.executeTransaction(transaction);
            isOperationSuccess = true;
        }
        return isOperationSuccess;
    }

    /**
     * Adds money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean add(double amount) {
        boolean isOperationSuccess = false;
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(-amount, this, null);
            transactionManager.executeTransaction(transaction);
            isOperationSuccess = true;
        }
        return isOperationSuccess;
    }

    public Collection<Entry> history(LocalDate from, LocalDate to) {
        if (from.compareTo(to) > 0) throw new IllegalArgumentException("\"From\" must be less then \"to\"");
        Collection<Entry> historyOfEntry = entries.betweenDates(from, to);
        return historyOfEntry;
    }

    /**
     * Calculates balance on the accounting entries basis
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        double balance = 0;
        if (entries.first() == null) return 0;
        LocalDate dateOfFirstEntry = LocalDate.from(entries.first().getTime());
        ArrayList<Entry> historyBeforeDate = (ArrayList<Entry>) history(dateOfFirstEntry, date);
        for (Entry entry: historyBeforeDate) {
            balance += entry.getAmount();
        }
        return balance;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
         ArrayList<Transaction> transactionArrayList = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(this);
         Transaction lastTransaction = transactionArrayList.get(transactionArrayList.size()-1);
         transactionManager.rollbackTransaction(lastTransaction);
    }
}
    