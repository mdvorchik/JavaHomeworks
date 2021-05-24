package ru.sbt.mipt.homework.hw1;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

public class AccountTest {

    @Test(expected = IllegalArgumentException.class)
    public void accountConstructorTest() {
        //given
        Account account = new Account(-3);
        //verify
        assertThrows(IllegalArgumentException.class, () -> account.add(3));
    }

    @Test
    public void withdrawWhenZeroBalance() {
        //given
        Account account = new Account(4);
        //when
        boolean successOperation = account.withdraw(-5);
        //verify
        assertFalse(successOperation);
    }

    @Test
    public void withdrawZeroFromBalance() {
        //given
        Account account = new Account(4);
        //when
        boolean successOperation = account.withdraw(0);
        //verify
        assertFalse(successOperation);
    }

    @Test
    public void withdrawNegativeFromBalance() {
        //given
        Account account = new Account(4);
        //when
        boolean successOperation = account.withdraw(-5);
        //verify
        assertFalse(successOperation);
    }

    @Test
    public void withdrawWhenPositiveBalance() {
        //given
        Account account = new Account(4);
        account.add(30);
        //when
        boolean successOperation = account.withdraw(14);
        assumeTrue(successOperation);
        //verify
        assertEquals(16, account.getBalance(), 0.0001);
    }

    @Test
    public void addZero() {
        //given
        Account account = new Account(4);
        //when
        boolean successOperation = account.add(0);
        //verify
        assertFalse(successOperation);
    }

    @Test
    public void addNegative() {
        //given
        Account account = new Account(4);
        //when
        boolean successOperation = account.add(-5);
        //verify
        assertFalse(successOperation);
    }

    @Test
    public void addPositiveOneTime() {
        //given
        Account account = new Account(4);
        //when
        boolean successOperation = account.add(20);
        assumeTrue(successOperation);
        //verify
        assertEquals(20, account.getBalance(), 0.0001);
    }

    @Test
    public void addPositiveSomeTime() {
        //given
        Account account = new Account(4);
        account.add(5);
        //when
        account.add(15);
        boolean successOperation = account.add(30);
        assumeTrue(successOperation);
        //verify
        assertEquals(50, account.getBalance(), 0.0001);
    }
}