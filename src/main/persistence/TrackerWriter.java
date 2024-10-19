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
        return null;
    }

    // EFFECTS: returns accounts in JSON array
    public JSONArray transactionsToJsonArray(List<Transaction> transactions) {
        return null;
    }

    // EFFECTS: takes in an account and converts it into a JSON object and returns it
    public JSONObject transactionToJson(Transaction transaction) {
        return null;
    }

}
