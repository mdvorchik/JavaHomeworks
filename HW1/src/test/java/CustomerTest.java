import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp(){
        customer = new Customer("John", "Smith");
        customer.openAccount(3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest1() {
        String name = null;
        String surname = null;
        Customer customer = new Customer(name, surname);
        customer.fullName();
    }
    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest2() {
        String name = "John";
        String surname = null;
        Customer customer = new Customer(name, surname);
        customer.fullName();
    }
    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest3() {
        String name = null;
        String surname = "Smith";
        Customer customer = new Customer(name, surname);
        customer.fullName();
    }

    @Test
    public void openAccount() {
        Assert.assertFalse(customer.openAccount(3));
        Assert.assertTrue(customer.closeAccount());
        Assert.assertTrue(customer.openAccount(3));
        Assert.assertFalse(customer.openAccount(3));
        Assert.assertFalse(customer.openAccount(2));
    }

    @Test
    public void closeAccount() {
        Assert.assertTrue(customer.closeAccount());
        Assert.assertFalse(customer.closeAccount());
    }

    @Test
    public void fullName() {
        Assert.assertEquals("John Smith", customer.fullName());
        customer = new Customer("Billy", "Jin");
        Assert.assertEquals("Billy Jin", customer.fullName());
    }

    @Test
    public void withdrawFromCurrentAccount() {
        Assert.assertFalse(customer.withdrawFromCurrentAccount(2));
        customer.closeAccount();
        Assert.assertFalse(customer.withdrawFromCurrentAccount(2));
        customer.openAccount(3);
        customer.addMoneyToCurrentAccount(100);
        Assert.assertTrue(customer.withdrawFromCurrentAccount(50));
        Assert.assertFalse(customer.withdrawFromCurrentAccount(51));
        Assert.assertFalse(customer.withdrawFromCurrentAccount(0));
        Assert.assertTrue(customer.withdrawFromCurrentAccount(50));
    }

    @Test
    public void addMoneyToCurrentAccount() {
        Assert.assertTrue(customer.addMoneyToCurrentAccount(100));
        customer.closeAccount();
        Assert.assertFalse(customer.addMoneyToCurrentAccount(100));
        customer.openAccount(3);
        Assert.assertTrue(customer.addMoneyToCurrentAccount(200));
        Assert.assertFalse(customer.addMoneyToCurrentAccount(0));
    }
}