package persistence;

import model.Expense;
import model.Categories;

import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class CategoriesWriter extends JsonWriter {

    // EFFECTS: constructs writer to write to Categories JSON file
    public CategoriesWriter(String destination) {
        super(destination);
    }

    // EFFECTS: returns a JSONObject of the data that is going to be written
    public JSONObject toJson(Categories category) {
        JSONObject json = new JSONObject();
        json.put("Expenses", expensesToJsonArray(category.getExpense()));
        return json;
    }

    // EFFECTS: returns expenses in JSON array
    public JSONArray expensesToJsonArray(List<Expense> expenses) {
        JSONArray jsonArray = new JSONArray();
        for (Expense e : expenses) {
            jsonArray.put(expenseToJson(e));
        }
        return jsonArray;
    }

    // EFFECTS: takes in an expense and converts it into a JSON Object
    public JSONObject expenseToJson(Expense expense) {
        JSONObject json = new JSONObject();
        json.put("Name", expense.getExpense());
        json.put("Spending", expense.getSpending());
        return json;
    }

}
