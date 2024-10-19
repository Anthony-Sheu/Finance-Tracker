package persistence;

import model.Categories;
import model.Expense;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class CategoriesPersistenceTest {

    private String source;
    private CategoriesWriter writer;
    private CategoriesReader reader, readerEmpty;
    Categories category;
    Expense expense1, expense2;

    @BeforeEach
    void runBefore() {
        source = "./data/CategoriesTest.json";
        writer = new CategoriesWriter(source);
        reader = new CategoriesReader(source);
        readerEmpty = new CategoriesReader("./data/CategoriesTestEmpty.json");
        category = new Categories();
        expense1 = new Expense("Holiday");
        expense2 = new Expense("Field Trip");
    }

    @Test
    void testReadEmptyFile() {
        try {
            category = readerEmpty.toCategories(readerEmpty.read());
            fail("Error expected");
        } catch (IOException e) {
            // pass
        } catch (JSONException e) {
            // pass
        }
    }

    @Test
    void testPersistNewCategory() {
        try {
            category.addCategory(expense1);
            writer.open();
            writer.write(writer.toJson(category));
            writer.close();
            category = reader.toCategories(reader.read());
            assertNull(category.checkCategory("CPSC 210"));
            checkExpense(expense1, category.checkCategory("Holiday"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testPersistTwoCategory() {
        try {
            category.addCategory(expense1);
            category.addCategory(expense2);
            writer.open();
            writer.write(writer.toJson(category));
            writer.close();
            category = reader.toCategories(reader.read());
            checkExpense(expense1, category.checkCategory("Holiday"));
            checkExpense(expense2, category.checkCategory("Field Trip"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // EFFECTS: takes in two expenses and matches them by content
    private void checkExpense(Expense exp1, Expense exp2) {
        assertEquals(exp1.getExpense(), exp2.getExpense());
        assertEquals(exp1.getSpending(), exp2.getSpending());
    }

}
