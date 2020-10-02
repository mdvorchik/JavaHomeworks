package ru.sbt.hw2;

import java.time.LocalDateTime;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;

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
        Transaction transaction = this;
        boolean isOperationOfWithdrawSuccess = originator.withdraw(amount, beneficiary);
        boolean isOperationOfAddMoneySuccess = beneficiary.add(amount);
        if (isOperationOfAddMoneySuccess && isOperationOfWithdrawSuccess) {
            LocalDateTime timeWhenTransactionExecuted = LocalDateTime.now();
            Entry entryOriginator = new Entry(originator, this, -amount, timeWhenTransactionExecuted);
            Entry entryBeneficiary = new Entry(beneficiary, this, amount, timeWhenTransactionExecuted);
            originator.getEntries().addEntry(entryOriginator);
            beneficiary.getEntries().addEntry(entryBeneficiary);
            transaction = new Transaction(this.id, this.amount, this.originator, this.beneficiary, true, false);
        }
        return transaction;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        if (rolledBack) throw new IllegalStateException("This transaction was already rolled back");
        Transaction transaction = this;
        boolean isOperationOfWithdrawSuccess = beneficiary.withdraw(amount, originator);
        boolean isOperationOfAddMoneySuccess = originator.add(amount);
        if (isOperationOfAddMoneySuccess && isOperationOfWithdrawSuccess) {
            LocalDateTime timeWhenTransactionExecuted = LocalDateTime.now();
            Entry entryOriginator = new Entry(originator, this, amount, timeWhenTransactionExecuted);
            Entry entryBeneficiary = new Entry(beneficiary, this, -amount, timeWhenTransactionExecuted);
            originator.getEntries().addEntry(entryOriginator);
            beneficiary.getEntries().addEntry(entryBeneficiary);
            transaction = new Transaction(this.id, this.amount, this.originator, this.beneficiary, true, false);
        }
        return transaction;
    }
}
