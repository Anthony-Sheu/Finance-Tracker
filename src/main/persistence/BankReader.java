package persistence;

import model.Banks;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.*;
import java.io.IOException;

// Represents a reader that reads bank from JSON data stored in file
public class BankReader extends JsonReader {

    // EFFECTS: construcs reader to read Bank JSON file
    public BankReader(String source) {
        super(source);
        //TODO Auto-generated constructor stub
    }

    // EFFECTS: converts JSONObject to Bank
    public Banks toBanks(JSONObject json) {
        return null;
    }

}
