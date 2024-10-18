package persistence;

import model.Banks;
import model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankWriterTest {

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
        acc2 = new Account(1, 500, 600, "CIBC", 1000);
    }

    @Test
    void testWriteNewBank() {
        try {
            bank.newAccount(acc1);
            writer.open();
            writer.write(writer.toJson(bank));
            writer.close();

            bank = reader.toBanks(reader.read());
            assertEquals(acc1, bank.findAccount("CIBC"));
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
            assertEquals(acc1, bank.findAccount("CIBC"));
            assertNull(bank.findAccount("rainbow"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
