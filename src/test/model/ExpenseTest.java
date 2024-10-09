package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ExpenseTest {

    private Expense expense1;
    private Expense expense2;

    @BeforeEach
    public void runBefore() {
        expense1 = new Expense("Food");
        expense2 = new Expense("Car");
    }

    @Test
    public void testUpdateSpending() {
        expense1.updateSpending(100);
        expense2.updateSpending(0);
        assertEquals(100, expense1.getSpending());
        assertEquals(0, expense2.getSpending());
    }

}
