public class Customer {
    private final String name;
    private final String lastName;
    private Account account;

    public Customer(String name, String lastName) {
        if (name == null || lastName == null) {
            throw new IllegalArgumentException("name or lastName can't be empty");
        }
        this.name = name;
        this.lastName = lastName;
    }

    /**
     * Opens account for a customer (creates Account and sets it to field "account").
     * Customer can't have greater than one opened account.
     *
     * @param accountId id of the account
     * @return true if account hasn't already created, otherwise returns false and prints "Customer fullName() already has the active account"
     */
    public boolean openAccount(long accountId) {
        boolean successOperation = false;
        if (account == null) {
            account = new Account(accountId);
            successOperation = true;
        } else {
            System.out.println("Customer " + fullName() + " already has the active account");
        }
        return successOperation;
    }

    /**
     * Closes account. Sets account to null.
     *
     * @return false if account is already null and prints "Customer fullName() has no active account to close", otherwise sets account to null and returns true
     */
    public boolean closeAccount() {
        boolean successOperation = false;
        if (account != null) {
            account = null;
            successOperation = true;
        } else {
            System.out.println("Customer " + fullName() + " has no active account to close");
        }
        return successOperation;
    }

    /**
     * Formatted full name of the customer
     * @return concatenated form of name and lastName, e.g. "John Goodman"
     */
    public String fullName() {
        return name + " " + lastName;
    }

    /**
     * Delegates withdraw to Account class
     * @param amount
     * @return false if account is null and prints "Customer fullName() has no active account", otherwise returns the result of Account's withdraw method
     */
    public boolean withdrawFromCurrentAccount(double amount) {
        boolean successOperation = false;
        if (account != null) {
            successOperation = account.withdraw(amount);
        } else {
            System.out.println("Customer " + fullName() + " has no active account");
        }
        return successOperation;
    }

    /**
     * Delegates adding money to Account class
     * @param amount
     * @return false if account is null and prints "Customer fullName() has no active account", otherwise returns the result of Account's add method
     */
    public boolean addMoneyToCurrentAccount(double amount) {
        boolean successOperation = false;
        if (account != null) {
            successOperation = account.add(amount);
        } else {
            System.out.println("Customer " + fullName() + " has no active account");
        }
        return successOperation;
    }
}



