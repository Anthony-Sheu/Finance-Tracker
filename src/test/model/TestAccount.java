package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAccount {
    
    private Account account;

    @BeforeEach
    void runBefore() {
        account = new Account(100, 500, 200, "CIBC", 1500);
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
        assertEquals(false, account.checkOverdraftSavings());
    }

    @Test
    void testCredit() {
        account.updateCredit(100);
        assertEquals(300, account.getCredit());
        account.updateCredit(5000);
        assertEquals(5300, account.getCredit());
        assertEquals(true, account.checkOverLimit());
    }

    @Test
    void testOverdraft() {
        account.updateChequeing(-500);
        assertEquals(-400, account.getChequeing());
        assertEquals(true, account.checkOverdraftChequeing());
        account.updateSavings(-600);
        assertEquals(true, account.checkOverdraftSavings());
    }

}
