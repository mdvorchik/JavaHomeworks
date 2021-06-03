package ru.sbt.mipt.homework.hw3;

import java.time.LocalDate;
import java.util.Collection;

public interface Account {
    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    boolean withdraw(double amount, DebitCard beneficiary);

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    boolean withdrawCash(double amount);

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    boolean addCash(double amount);

    /**
     * Adds entry to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param entry - entry
     */
    void addEntry(Entry entry);

    /**
     * Adds money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    boolean add(double amount);

    Collection<Entry> history(LocalDate from, LocalDate to);

    /**
     * Calculates balance on the accounting entries basis
     *
     * @param date - date
     * @return balance
     */
    double balanceOn(LocalDate date);

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    void rollbackLastTransaction();
}