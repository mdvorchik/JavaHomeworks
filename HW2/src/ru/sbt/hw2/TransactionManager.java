package ru.sbt.hw2;

public class TransactionManager {
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
        // write your code here
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        // write your code here
    }


    public void rollbackTransaction(Transaction transaction) {
        // write your code here
    }

    public void executeTransaction(Transaction transaction) {
        // write your code here
    }
}
