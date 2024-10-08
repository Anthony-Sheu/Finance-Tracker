package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BanksTest {

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

    @Test
    void testTransfer() {
        bank.transfer("CIBC", "Chequeing", "RBC",  "Savings", 50);
        assertEquals(50, account1.getChequeing());
        assertEquals(1050, account2.getSavings());
    }

    @Test
    void testBank() {
        List<Account> temp = new ArrayList<>();
        temp.add(account1);
        temp.add(account2);
        assertEquals(temp, bank.getBank());
    }

    @Test
    void testUpdateTransfer() {
        bank.updateTransfer(account1, "Credit", 100);
        assertEquals(300, account1.getCredit());
    }

}
