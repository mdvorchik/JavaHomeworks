package ru.sbt.mipt.homework.hw1;

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
     * Opens account for a customer (creates ru.sbt.mipt.homework.hw1.Account and sets it to field "account").
     * ru.sbt.mipt.homework.hw1.Customer can't have greater than one opened account.
     *
     * @param accountId id of the account
     * @return true if account hasn't already created, otherwise returns false and prints "ru.sbt.mipt.homework.hw1.Customer fullName() already has the active account"
     */
    public boolean openAccount(long accountId) {
        if (account == null) {
            account = new Account(accountId);
            return true;
        }
        System.out.println("ru.sbt.mipt.homework.hw1.Customer " + fullName() + " already has the active account");
        return false;
    }

    /**
     * Closes account. Sets account to null.
     *
     * @return false if account is already null and prints "ru.sbt.mipt.homework.hw1.Customer fullName() has no active account to close", otherwise sets account to null and returns true
     */
    public boolean closeAccount() {
        if (account != null) {
            account = null;
            return true;
        }
        System.out.println("ru.sbt.mipt.homework.hw1.Customer " + fullName() + " has no active account to close");
        return false;
    }

    /**
     * Formatted full name of the customer
     * @return concatenated form of name and lastName, e.g. "John Goodman"
     */
    public String fullName() {
        return name + " " + lastName;
    }

    /**
     * Delegates withdraw to ru.sbt.mipt.homework.hw1.Account class
     *
     * @param amount
     * @return false if account is null and prints "ru.sbt.mipt.homework.hw1.Customer fullName() has no active account", otherwise returns the result of ru.sbt.mipt.homework.hw1.Account's withdraw method
     */
    public boolean withdrawFromCurrentAccount(double amount) {
        if (account != null) return account.withdraw(amount);
        System.out.println("ru.sbt.mipt.homework.hw1.Customer " + fullName() + " has no active account");
        return false;
    }

    /**
     * Delegates adding money to ru.sbt.mipt.homework.hw1.Account class
     * @param amount
     * @return false if account is null and prints "ru.sbt.mipt.homework.hw1.Customer fullName() has no active account", otherwise returns the result of ru.sbt.mipt.homework.hw1.Account's add method
     */
    public boolean addMoneyToCurrentAccount(double amount) {
        if (account != null) return account.add(amount);
        System.out.println("ru.sbt.mipt.homework.hw1.Customer " + fullName() + " has no active account");
        return false;
    }
}



