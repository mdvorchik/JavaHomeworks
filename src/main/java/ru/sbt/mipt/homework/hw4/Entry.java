package ru.sbt.mipt.homework.hw4;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
public class Entry implements Comparable<Entry> {
    private final Account account;
    private final Transaction transaction;
    private final double amount;
    private final LocalDateTime time;

    public Entry(Account account, Transaction transaction, double amount, LocalDateTime time) {
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Double.compare(entry.amount, amount) == 0 &&
                Objects.equals(account, entry.account) &&
                Objects.equals(transaction, entry.transaction) &&
                Objects.equals(time, entry.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, transaction, amount, time);
    }

    @Override
    public int compareTo(Entry o) {
        return this.time.compareTo(o.time);
    }
}

