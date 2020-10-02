package ru.sbt.hw2;

import java.time.LocalDateTime;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;

    Account getOriginator() {
        return originator;
    }

    Account getBeneficiary() {
        return beneficiary;
    }

    public double getAmount() {
        return amount;
    }

    public Transaction(long id, double amount, Account originator, Account beneficiary, boolean executed, boolean rolledBack) {
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    /**
     * Adding entries to both accounts
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        if (executed) throw new IllegalStateException("This transaction was already executed");
        Transaction transaction;
        LocalDateTime timeWhenTransactionExecuted = LocalDateTime.now();
        if (originator != null) {
            Entry entryOriginator = new Entry(originator, this, -amount, timeWhenTransactionExecuted);
            originator.getEntries().addEntry(entryOriginator);
        }
        if (beneficiary != null) {
            Entry entryBeneficiary = new Entry(beneficiary, this, amount, timeWhenTransactionExecuted);
            beneficiary.getEntries().addEntry(entryBeneficiary);
        }
        transaction = new Transaction(this.id, this.amount, this.originator, this.beneficiary, true, false);
        return transaction;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        if (!executed) throw new IllegalStateException("This transaction was not execut");
        if (rolledBack) throw new IllegalStateException("This transaction was already rolled back");
        Transaction transaction;
        LocalDateTime timeWhenTransactionExecuted = LocalDateTime.now();
        if (originator != null) {
            Entry entryOriginator = new Entry(originator, this, amount, timeWhenTransactionExecuted);
            originator.getEntries().addEntry(entryOriginator);
        }
        if (beneficiary != null) {
            Entry entryBeneficiary = new Entry(beneficiary, this, -amount, timeWhenTransactionExecuted);
            beneficiary.getEntries().addEntry(entryBeneficiary);
        }
        transaction = new Transaction(this.id, this.amount, this.originator, this.beneficiary, true, true);
        return transaction;
    }
}
