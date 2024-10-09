package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class TrackerTest {

    private Tracker tracker;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Account account;
    private List<Transaction> check;

    @BeforeEach
    void runBefore() {
        tracker = new Tracker();
        check = new ArrayList<>();
        account = new Account(200, 5000, 0, "CIBC", 1500);
        transaction1 = new Transaction(9, 1, 2024, 1500.00, "Interac", "Rent", "", "CIBC", "Chequeing");
        transaction2 = new Transaction(9, 20, 2024, 20.00, "McDonald's", "Food", "", "CIBC", "Credit");
        transaction3 = new Transaction(10, 2, 2024, -5.00, "Bookstore", "Education", "Refund", "CIBC", "Credit");
        transaction4 = new Transaction(9, 27, 2023, 10.00, "Pizza Hut", "Food", "", "CIBC", "Chequeing");
    }

    @Test
    void testConstructor() {
        assertEquals(check, tracker.getTracker());
    }

    @Test
    void testUpdateTransaction() {
        tracker.addTransaction(transaction1);
        check.add(transaction1);
        assertEquals(check, tracker.getTracker());
        tracker.removeTransaction(transaction1);
        check.remove(0);
        assertEquals(check, tracker.getTracker());
    }

    @Test
    void testSortMonth() {
        check.add(transaction1);
        check.add(transaction2);
        tracker.addTransaction(transaction1);
        tracker.addTransaction(transaction2);
        tracker.addTransaction(transaction3);
        tracker.addTransaction(transaction4);
        assertEquals(check, tracker.sortMonth(9, 2024));
    }

    @Test
    void testSortExpense() {
        check.add(transaction2);
        check.add(transaction4);
        tracker.addTransaction(transaction1);
        tracker.addTransaction(transaction2);
        tracker.addTransaction(transaction3);
        tracker.addTransaction(transaction4);
        assertEquals(check, tracker.sortExpense("Food"));
    }
    
    @Test
    void testPrint() {
        tracker.addTransaction(transaction1);
        assertEquals("9/1/2024, $1500.0, Interac, Rent, No note, CIBC Chequeing", transaction1.printTransaction());
    }

    @Test
    void testFindTransaction() {
        tracker.addTransaction(transaction1);
        tracker.addTransaction(transaction2);
        assertEquals(transaction1, tracker.findTransaction(0));
    }

}
