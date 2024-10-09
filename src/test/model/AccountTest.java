package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
    
    private Account account;

    @BeforeEach
    void runBefore() {
        account = new Account(100, 500, 200, "CIBC", 1500);
    }

    @Test
    void testConstructor() {
        assertEquals(100, account.getChequeing());
        assertEquals(500, account.getSavings());
        assertEquals(200, account.getCredit());
        assertEquals(1500, account.getCreditLimit());
    }

    @Test
    void testBank() {
        assertEquals("CIBC", account.getBank());
    }

    @Test
    void testChequeing() {
        account.updateChequeing(100);
        assertEquals(200, account.getChequeing());
        account.updateChequeing(-100);
        assertEquals(100, account.getChequeing());
    }

    @Test
    void testSavings() {
        account.updateSavings(100);
        assertEquals(600, account.getSavings());
        account.updateSavings(-100);
        assertEquals(500, account.getSavings());
        assertFalse(account.checkOverdraftSavings());
    }

    @Test
    void testCredit() {
        account.updateCredit(100);
        assertEquals(300, account.getCredit());
        account.updateCredit(5000);
        assertEquals(5300, account.getCredit());
        assertTrue(account.checkOverLimit());
        account.updateCreditLimit(5300);
        assertFalse(account.checkOverLimit());
        account.updateCreditLimit(2000);
        assertTrue(account.checkOverLimit());
    }

    @Test
    void testOverdraft() {
        account.updateChequeing(-500);
        assertEquals(-400, account.getChequeing());
        assertTrue(account.checkOverdraftChequeing());
        account.updateSavings(-600);
        assertTrue(account.checkOverdraftSavings());
    }

    @Test
    void testPrint() {
        String s = "CIBC\nChequeing:";
        s += " $100.0\nSavings:";
        s += " $500.0\nCredit:";
        s += " $200.0\nCredit Limit: $1500.0";
        assertEquals(s, account.printAccount());
    }

    @Test
    void testRefund() {
        account.refund("Chequeing", 100);
        assertEquals(200, account.getChequeing());
        account.refund("Savings", 100);
        assertEquals(600, account.getSavings());
        account.refund("Credit", 100);
        assertEquals(100, account.getCredit());
    }
}
