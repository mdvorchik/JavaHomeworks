import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp(){
        customer = new Customer("John", "Smith");
        customer.openAccount(3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest_NullName_And_Surname() {
        String name = null;
        String surname = null;
        Customer customer = new Customer(name, surname);
        customer.fullName();
    }

    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest_NullSurname() {
        String name = "John";
        String surname = null;
        Customer customer = new Customer(name, surname);
        customer.fullName();
    }
    @Test (expected = IllegalArgumentException.class)
    public void customerConstructorTest_NullName() {
        String name = null;
        String surname = "Smith";
        Customer customer = new Customer(name, surname);
        customer.fullName();
    }

    @Test
    public void openExistAccount() {
        Assert.assertFalse(customer.openAccount(3));
    }

    @Test
    public void openNonExistAccount() {
        customer = new Customer("Jhon", "Sina");
        Assert.assertTrue(customer.openAccount(3));
    }

    @Test
    public void openClosedAccount() {
        Assert.assertTrue(customer.closeAccount());
        Assert.assertTrue(customer.openAccount(3));
    }

    @Test
    public void closeOpenedAccount() {
        Assert.assertTrue(customer.closeAccount());
    }

    @Test
    public void closeClosedAccount() {
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
    public void withdrawFromCurrentOpenedAccount() {
        customer.addMoneyToCurrentAccount(100);
        Assert.assertTrue(customer.withdrawFromCurrentAccount(20)); // 20 < 100
    }

    @Test
    public void withdrawFromCurrentClosedAccount() {
        customer.addMoneyToCurrentAccount(100);
        customer.closeAccount();
        Assert.assertFalse(customer.withdrawFromCurrentAccount(20));
    }

    @Test
    public void addMoneyToOpenedAccount() {
        Assert.assertTrue(customer.addMoneyToCurrentAccount(100));
    }

    @Test
    public void addMoneyToClosedAccount() {
        customer.closeAccount();
        Assert.assertFalse(customer.addMoneyToCurrentAccount(100));
    }
}