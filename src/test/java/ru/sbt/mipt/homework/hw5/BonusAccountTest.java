package ru.sbt.mipt.homework.hw5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

public class BonusAccountTest {
    @Test
    public void withdrawWithBonus() {
        //given
        TransactionManager transactionManager = new TransactionManager(new HashMap<>(), new ArrayList<>());
        BonusAccount account = new BonusAccount(new DebitCard(1, transactionManager), 10);
        account.add(100);
        //when
        assumeTrue(account.withdraw(100, new DebitCard(2, transactionManager)));
        //verify
        assertEquals(10, account.getBonusScore(), 0.0001);
    }

    @Test
    public void withdrawTwiceWithBonus() {
        //given
        TransactionManager transactionManager = new TransactionManager(new HashMap<>(), new ArrayList<>());
        BonusAccount account = new BonusAccount(new DebitCard(1, transactionManager), 10);
        account.add(100);
        //when
        assumeTrue(account.withdraw(50, new DebitCard(2, transactionManager)));
        assumeTrue(account.withdraw(50, new DebitCard(2, transactionManager)));
        //verify
        assertEquals(10, account.getBonusScore(), 0.0001);
    }
}
