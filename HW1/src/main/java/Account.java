public class Account {
    private final long id;
    private double balance;

    public Account(long id) {
        if (id < 0){
            throw new IllegalArgumentException("id can't be &lt 0");
        }
        this.id = id;
    }

    public double getBalance(){
        return balance;
    }

    /**
     * Withdraws money from account balance
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (balance - amount) &gt 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount) {
        boolean successOperation = false;
        if (amount > 0 && balance - amount >= 0) {
            balance -= amount;
            successOperation = true;
        }
        return successOperation;
    }

    /**
     * Adds money to account balance
     *
     * @param amount amount of money to add on account
     * @return true if amount &gt 0, otherwise returns false
     */
    public boolean add(double amount) {
        boolean successOperation = false;
        if (amount > 0) {
            balance += amount;
            successOperation = true;
        }
        return successOperation;
    }
}