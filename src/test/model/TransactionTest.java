package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Account account;

    @BeforeEach
    void runBefore() {
        account = new Account(200, 5000, 0, "CIBC", 1500);
        transaction1 = new Transaction(9, 1, 2024, 1500.00, "Interac", "Rent", "", "CIBC", "Chequeing");
        transaction2 = new Transaction(9, 20, 2024, 20.00, "McDonald's", "Food", "", "CIBC", "Credit");
        transaction3 = new Transaction(10, 2, 2024, -5.00, "Bookstore", "Education", "Refund", "CIBC", "Credit");
        transaction4 = new Transaction(9, 27, 2023, 10.00, "Pizza Hut", "Food", "", "CIBC", "Chequeing");
    }

    @Test
    void testConstructor() {
        assertEquals(9, transaction1.getMonth());
        assertEquals(2024, transaction1.getYear());
        assertEquals("Rent", transaction1.getExpense());
        assertEquals("CIBC", transaction1.getAccountName());
        assertEquals("Chequeing", transaction1.getAccountType());
        assertEquals(1500, transaction1.getAmount());
    }

    @Test
    void testUpdateAmount() {
        transaction1.updateAmount(1600);
        assertEquals(1600, transaction1.getAmount());
    }

}
