package persistence;

import org.json.JSONObject;

import model.Tracker;
import model.Transaction;

public class TrackerReader extends JsonReader {

    // EFFECTS: constructs reader to read from Tracker JSON file
    public TrackerReader(String source) {
        super(source);
    }

    // EFFECTS: converts JSOB object to Bank
    public Tracker toTracker(JSONObject json) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: converts JSON object to transaction and adds it to tracker
    public void toTransaction(Tracker tracker, JSONObject json) {
        
    }

}
