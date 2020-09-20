import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test (expected = IllegalArgumentException.class)
    public void accountConstructorTest() {
        Account account = new Account(-3);
        account.add(3);
    }

    @Test
    public void withdraw() {
        Account account = new Account(4);
        boolean successOperation = account.withdraw(-5);
        Assert.assertFalse(successOperation);
        successOperation = account.withdraw(0);
        Assert.assertFalse(successOperation);
        account.add(30);
        successOperation = account.withdraw(14);
        Assert.assertTrue(successOperation);
        Assert.assertEquals(16, account.getBalance(), 0.0001);
    }

    @Test
    public void add() {
        Account account = new Account(4);
        boolean successOperation = account.add(-5);
        Assert.assertFalse(successOperation);
        successOperation = account.add(0);
        Assert.assertFalse(successOperation);
        successOperation = account.add(20);
        Assert.assertTrue(successOperation);
        Assert.assertEquals(20, account.getBalance(), 0.0001);
    }
}