import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    @Test (expected = IllegalArgumentException.class)
    public void accountConstructorTest() {
        Account account = new Account(-3);
        account.add(3);
    }

    @Test
    public void withdrawWhenZeroBalance() {
        Account account = new Account(4);
        boolean successOperation = account.withdraw(-5);
        Assert.assertFalse(successOperation);
    }

    @Test
    public void withdrawZeroFromBalance() {
        Account account = new Account(4);
        boolean successOperation = account.withdraw(0);
        Assert.assertFalse(successOperation);
    }

    @Test
    public void withdrawNegativeFromBalance() {
        Account account = new Account(4);
        boolean successOperation = account.withdraw(-5);
        Assert.assertFalse(successOperation);
    }

    @Test
    public void withdrawWhenPositiveBalance() {
        Account account = new Account(4);
        account.add(30);
        boolean successOperation = account.withdraw(14); // 30 - 14 = 16 > 0 => Success
        Assert.assertTrue(successOperation);
        Assert.assertEquals(16, account.getBalance(), 0.0001);
    }

    @Test
    public void addZero() {
        Account account = new Account(4);
        boolean successOperation = account.add(0);
        Assert.assertFalse(successOperation);
    }

    @Test
    public void addNegative() {
        Account account = new Account(4);
        boolean successOperation = account.add(-5);
        Assert.assertFalse(successOperation);
    }

    @Test
    public void addPositiveOneTime() {
        Account account = new Account(4);
        boolean successOperation = account.add(20);
        Assert.assertTrue(successOperation);
        Assert.assertEquals(20, account.getBalance(), 0.0001);
    }

    @Test
    public void addPositiveSomeTime() {
        Account account = new Account(4);
        account.add(5);
        account.add(15);
        boolean successOperation = account.add(30);
        Assert.assertTrue(successOperation);
        Assert.assertEquals(50, account.getBalance(), 0.0001); // 50 = 5 + 15 + 30
    }
}