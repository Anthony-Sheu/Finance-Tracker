package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAccount {
    
    private Account account;

    @BeforeEach
    void runBefore() {
        account = new Account(100, 500, 200);
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
    }

    @Test
    void testCredit() {
        account.updateCredit(100);
        assertEquals(300, account.getCredit());
        account.updateCredit(100);
        assertEquals(200, account.getCredit());
    }

    @Test
    void testOverdraft() {
        account.updateChequeing(-500);
        assertEquals(-400, account.getChequeing());
        assertEquals(true, account.checkOverdraft());
    }

}
