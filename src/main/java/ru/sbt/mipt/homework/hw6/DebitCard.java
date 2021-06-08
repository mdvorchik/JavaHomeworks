package ru.sbt.mipt.homework.hw6;

import java.time.LocalDate;
import java.util.*;

public class DebitCard implements Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;

    Entries getEntries() {
        return entries;
    }

    public DebitCard(long id, TransactionManager transactionManager) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.entries = new Entries(new TreeMap<>());
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    @Override
    public boolean withdraw(double amount, Account beneficiary) {
        LocalDate localDate = LocalDate.now();
        if (amount > 0 && this.balanceOn(localDate) - amount >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, beneficiary);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    @Override
    public boolean withdrawCash(double amount) {
        return withdraw(amount, null);
    }

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    @Override
    public boolean addCash(double amount) {
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(amount, null, this);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Adds entry to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param entry - entry
     */
    @Override
    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    /**
     * Adds money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    @Override
    public boolean add(double amount) {
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(-amount, this, null);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Entry> history(LocalDate from, LocalDate to) {
        if (from.compareTo(to) > 0) throw new IllegalArgumentException("\"From\" must be less then \"to\"");
        return entries.betweenDates(from, to);
    }

    @Override
    public double balanceOn(LocalDate date) {
        double balance = 0;
        if (entries.first() == null) return 0;
        LocalDate dateOfFirstEntry = LocalDate.from(entries.first().getTime());
        List<Entry> historyBeforeDate = (ArrayList<Entry>) history(dateOfFirstEntry, date);
        for (Entry entry : historyBeforeDate) {
            balance += entry.getAmount();
        }
        return balance;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    @Override
    public void rollbackLastTransaction() {
        List<Transaction> transactionArrayList = (ArrayList<Transaction>) transactionManager.findAllTransactionsByAccount(this);
        Transaction lastTransaction = transactionArrayList.get(transactionArrayList.size() - 1);
        transactionManager.rollbackTransaction(lastTransaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebitCard debitCard = (DebitCard) o;
        return id == debitCard.id &&
                Objects.equals(transactionManager, debitCard.transactionManager) &&
                Objects.equals(entries, debitCard.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionManager, entries);
    }
}
    