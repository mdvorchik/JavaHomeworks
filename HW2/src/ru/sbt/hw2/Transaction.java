package ru.sbt.hw2;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;

    public Transaction(long id, double amount, Account originator, Account beneficiary) {
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = false;
        this.rolledBack = false;
    }

    /**
     * Adding entries to both accounts
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        if (executed) throw new IllegalStateException("This transaction was already executed");
        boolean isOperationOfWithdrawSuccess = originator.withdraw(amount, beneficiary);
        boolean isOperationOfAddMoneySuccess = beneficiary.add(amount);
        if (isOperationOfAddMoneySuccess && isOperationOfWithdrawSuccess) {
            
        }


    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        // write your code here
    }
}
