package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Tracker;
import model.Transaction;

public class TrackerReader extends JsonReader {

    // EFFECTS: constructs reader to read from Tracker JSON file
    public TrackerReader(String source) {
        super(source);
    }

    // EFFECTS: converts JSOB object to Bank
    // citation
    public Tracker toTracker(JSONObject json) {
        Tracker tracker = new Tracker();
        JSONArray jsonArray = json.getJSONArray("Transactions");
        for (Object o : jsonArray) {
            JSONObject nextJson = (JSONObject) o;
            toTransaction(tracker, nextJson);
        }
        return tracker;
    }

    // MODIFIES: this
    // EFFECTS: converts JSON object to transaction and adds it to tracker
    public void toTransaction(Tracker tracker, JSONObject json) {
        int month = json.getInt("Month");
        int date = json.getInt("Date");
        int year = json.getInt("Year");
        double amount = json.getDouble("Amount");
        String store = json.getString("Store");
        String expense = json.getString("Expense");
        String note = json.getString("Note");
        String accName = json.getString("Account Name");
        String accType = json.getString("Account Type");
        tracker.addTransaction(new Transaction(month, date, year, amount, store, expense, note, accName, accType));
    }

}
