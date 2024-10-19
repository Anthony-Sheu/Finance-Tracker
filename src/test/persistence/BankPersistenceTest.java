package persistence;

import model.Banks;
import model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BankPersistenceTest {

    private String destination;
    private BankWriter writer;
    private BankReader reader;
    private Banks bank;
    private Account acc1, acc2;

    @BeforeEach
    void runBefore() {
        destination = "./data/BankTest.json";
        writer = new BankWriter(destination);
        reader = new BankReader(destination);
        bank = new Banks();
        acc1 = new Account(100, 1000, 0, "CIBC", 1500);
        acc2 = new Account(1, 500, 600, "RBC", 1000);
    }

    @Test
    void testWriteNewBank() {
        try {
            bank.newAccount(acc1);
            writer.open();
            writer.write(writer.toJson(bank));
            writer.close();
            bank = reader.toBanks(reader.read());
            Account acc = bank.findAccount("CIBC");
            checkAccount(acc, acc1);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteTwoBank() {
        try {
            bank.newAccount(acc1);
            bank.newAccount(acc2);
            writer.open();
            writer.write(writer.toJson(bank));
            writer.close();
            bank = reader.toBanks(reader.read());
            assertNull(bank.findAccount("rainbow"));
            checkAccount(acc1, bank.findAccount("CIBC"));
            checkAccount(acc2, bank.findAccount("RBC"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // EFFECTS: takes in two accounts and checks that their contents are the same
    private void checkAccount(Account acc1, Account acc2) {
        assertEquals(acc1.getBank(), acc2.getBank());
        assertEquals(acc1.getChequeing(), acc2.getChequeing());
        assertEquals(acc1.getSavings(), acc2.getSavings());
        assertEquals(acc1.getCredit(), acc2.getCredit());
        assertEquals(acc1.getCreditLimit(), acc2.getCreditLimit());
        assertEquals(acc1.checkOverdraftChequeing(), acc2.checkOverdraftChequeing());
        assertEquals(acc1.checkOverdraftSavings(), acc2.checkOverdraftSavings());
        assertEquals(acc1.checkOverLimit(), acc2.checkOverLimit());
    }

}
