package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCategories {

    private Categories category;
    private Expense expense;

    @BeforeEach
    void runBefore() {
        expense = new Expense("Rent");
        category = new Categories();
    }

    @Test
    void testConstructor() {
        assertEquals(null, category.checkCategory(""));
    }

    @Test
    void testUpdateCategory() {
        category.addCategory(expense);
        assertEquals(expense, category.checkCategory("Rent"));
        category.removeCategory(expense);
        assertEquals(null, category.checkCategory("Rent"));
    }

    @Test
    void testUpdateExpense() {
        category.addCategory(expense);
        category.updateExpense("Rent", "Groceries");
        assertEquals("Groceries", category.checkCategory("Groceries").getExpense());
        assertEquals(null, category.checkCategory("Rent"));
    }


}
