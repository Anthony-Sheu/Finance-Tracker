package persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

import model.Tracker;
import model.Transaction;

public class TrackerWriter extends JsonWriter {

    // EFFECTS: constructs writer to write to Tracker JSON file
    public TrackerWriter(String destination) {
        super(destination);
    }

    // EFFECTS: returns a JSOB object of the data that is going to be written
    public JSONObject toJson(Tracker tracker) {
        JSONObject json = new JSONObject();
        json.put("Transactions", transactionsToJsonArray(tracker.getTracker()));
        return json;
    }

    // EFFECTS: returns accounts in JSON array
    public JSONArray transactionsToJsonArray(List<Transaction> transactions) {
        JSONArray jsonArray = new JSONArray();
        for (Transaction t : transactions) {
            jsonArray.put(transactionToJson(t));
        }
        return jsonArray;
    }

    // EFFECTS: takes in an account and converts it into a JSON object and returns it
    public JSONObject transactionToJson(Transaction transaction) {
        JSONObject json = new JSONObject();
        json.put("Month", transaction.getMonth());
        json.put("Date", transaction.getDate());
        json.put("Year", transaction.getYear());
        json.put("Amount", transaction.getAmount());
        json.put("Store", transaction.getStore());
        json.put("Expense", transaction.getExpense());
        json.put("Note", transaction.getNote());
        json.put("Account Name", transaction.getAccountName());
        json.put("Account Type", transaction.getAccountType());
        return json;
    }

}
