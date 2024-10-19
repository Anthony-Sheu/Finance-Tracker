package persistence;

import model.Categories;
import model.Expense;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;

public class CategoriesPersistenceTest {

    private String source;
    private CategoriesWriter writer;
    private CategoriesReader reader;
    Categories category;
    Expense expense1, expense2;
    List<Expense> arr;

    @BeforeEach
    void runBefore() {
        source = "./data/CategoriesTest.json";
        writer = new CategoriesWriter(source);
        reader = new CategoriesReader(source);
        category = new Categories();
        expense1 = new Expense("Holiday");
        expense2 = new Expense("Field Trip");
        arr = new ArrayList<>();
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
            arr.add(expense1);
            assertEquals(arr, category.getExpense());
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
            assertEquals(expense1.getExpense(), category.checkCategory("Holiday"));
            arr.add(expense1);
            arr.add(expense2);
            assertEquals(arr, category.getExpense());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
