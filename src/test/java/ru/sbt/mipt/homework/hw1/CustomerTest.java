package ru.sbt.mipt.homework.hw1;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

public class CustomerTest {

    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest_NullName_And_Surname() {
        //given
        String name = null;
        String surname = null;
        Customer customer = new Customer(name, surname);
        //when
        assertThrows(IllegalArgumentException.class, customer::fullName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest_NullSurname() {
        //given
        String name = "John";
        String surname = null;
        Customer customer = new Customer(name, surname);
        //when
        assertThrows(IllegalArgumentException.class, customer::fullName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest_NullName() {
        //given
        String name = null;
        String surname = "Smith";
        Customer customer = new Customer(name, surname);
        //when
        assertThrows(IllegalArgumentException.class, customer::fullName);
    }

    @Test
    public void openExistAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        //when
        customer.openAccount(3);
        //verify
        assertFalse(customer.openAccount(3));
    }

    @Test
    public void openNonExistAccount() {
        //given
        Customer customer = new Customer("Jhon", "Sina");
        //verify
        assertTrue(customer.openAccount(3));
    }

    @Test
    public void openClosedAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.openAccount(3);
        //when
        assumeTrue(customer.closeAccount());
        //verify
        assertTrue(customer.openAccount(3));
    }

    @Test
    public void closeOpenedAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        //when
        customer.openAccount(3);
        //verify
        assertTrue(customer.closeAccount());
    }

    @Test
    public void closeClosedAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.openAccount(3);
        //when
        assumeTrue(customer.closeAccount());
        //verify
        assertFalse(customer.closeAccount());
    }

    @Test
    public void fullName() {
        //given
        Customer customer = new Customer("John", "Smith");
        //verify
        Assert.assertEquals("John Smith", customer.fullName());
    }

    @Test
    public void withdrawFromCurrentOpenedAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.openAccount(3);
        customer.addMoneyToCurrentAccount(100);
        //verify
        assertTrue(customer.withdrawFromCurrentAccount(20));
    }

    @Test
    public void withdrawFromCurrentClosedAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.openAccount(3);
        customer.addMoneyToCurrentAccount(100);
        //when
        customer.closeAccount();
        //verify
        assertFalse(customer.withdrawFromCurrentAccount(20));
    }

    @Test
    public void addMoneyToOpenedAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.openAccount(3);
        //verify
        assertTrue(customer.addMoneyToCurrentAccount(100));
    }

    @Test
    public void addMoneyToClosedAccount() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.openAccount(3);
        //when
        customer.closeAccount();
        //verify
        assertFalse(customer.addMoneyToCurrentAccount(100));
    }
}