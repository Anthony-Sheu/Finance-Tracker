package persistence;

import model.Categories;
import model.Expense;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.*;
import java.io.IOException;

public class CategoriesReader extends JsonReader {

    // EFFECTS: constructs reader to read from Categories JSON file
    public CategoriesReader(String source) {
        super(source);
    }

    // EFFECTS: converts JSONObject to Categories;
    public Categories toCategories(JSONObject json) {
        Categories category = new Categories();
        JSONArray jsonArray = json.getJSONArray("Expenses");
        for (Object o : jsonArray) {
            JSONObject nextJson = (JSONObject) o;
            toExpense(category, nextJson);
        }
        return category;
    }

    // MODIFIES: this
    // EFFECTS: converts JSONObject to Expense and adds it to category
    public void toExpense(Categories category, JSONObject json) {
        String name = json.getString("Name");
        double spending = json.getDouble("Spending");
        Expense expense = new Expense(name);
        expense.updateSpending(spending);
        category.addCategory(expense);
    }

}
