package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class CategoriesTest {

    private Categories category;
    private Expense expense1;
    private Expense expense2;

    @BeforeEach
    void runBefore() {
        expense1 = new Expense("Rent");
        expense2 = new Expense("Home");
        category = new Categories();
    }

    @Test
    void testConstructor() {
        assertEquals(null, category.checkCategory(""));
    }

    @Test
    void testUpdateCategory() {
        category.addCategory(expense1);
        assertEquals(expense1, category.checkCategory("Rent"));
        category.removeCategory(expense1);
        assertEquals(null, category.checkCategory("Rent"));
    }

    @Test
    void testGetExpense() {
        List<Expense> exp = new ArrayList<>();
        exp.add(expense1);
        exp.add(expense2);
        category.addCategory(expense1);
        category.addCategory(expense2);
        assertEquals(exp, category.getExpense());
    }

    @Test
    void testCheckCategory() {
        assertEquals(null, category.checkCategory("Food"));
        category.addCategory(expense1);
        assertEquals(expense1, category.checkCategory("Rent"));
        assertEquals(null, category.checkCategory("Food"));
    }


}
