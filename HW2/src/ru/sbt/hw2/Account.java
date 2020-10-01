package ru.sbt.hw2;

import java.util.ArrayList;
import java.util.Objects;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;

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
        //
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
        // write your code here
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
        // write your code here
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
        // write your code here
    }




    public Collection<Entry> history(LocalDate from, LocalDate to) {
        // write your code here
    }

    /**
     * Calculates balance on the accounting entries basis
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        // write your code here
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
        // write your code here
    }
}
    