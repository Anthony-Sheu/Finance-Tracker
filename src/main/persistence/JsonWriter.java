package persistence;

import model.Banks;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

// represents a write that writes JSON representation to file
public class JsonWriter {
    protected static final int TAB = 4;
    protected String destination;
    protected PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {

    }

    // MODIFIES: this
    // EFFECTS: opens writer
    // throws IOException if destination file cannot be found/error
    public void open() throws FileNotFoundException {

    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation to file
    public void write(JSONObject json) {

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {

    }

    // MODIFIES: this
    // EFFECTS: write to string to file
    public void save(String json) {

    }

}
