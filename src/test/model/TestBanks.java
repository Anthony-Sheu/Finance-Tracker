package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBanks {

    private Banks bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    void runBefore() {
        account1 = new Account(100, 500, 200, "CIBC", 1500);
        account2 = new Account(200, 1000, 0, "RBC", 2000);
        bank = new Banks();
        bank.newAccount(account1);
        bank.newAccount(account2);
    }

    @Test
    void testConstructor() {
        assertEquals(100, bank.getChequeing(account1));
        assertEquals(1000, bank.getSavings(account2));
        assertEquals(200, bank.getCredit(account1));
    }

    @Test
    void testFind() {
        assertEquals(account1, bank.findAccount("CIBC"));
        assertEquals(null, bank.findAccount("BMO"));
    }

}
