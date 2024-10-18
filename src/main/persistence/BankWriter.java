package persistence;

import org.json.JSONObject;
import model.Banks;

public class BankWriter extends JsonWriter {

    // EFFETCS: constructs a jsonwriter for bank
    public BankWriter(String destination) {
        super(destination);
    }

    // EFFECTS: returns a JSONObject of the data that
    // is going to be written
    public JSONObject toJson(Banks bank) {
        return null;
    }

}
