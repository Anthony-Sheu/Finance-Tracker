package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.*;

// Represents a reader that reads from JSON data in source file
public class JsonReader {

    protected String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads from source file and returns JSON object
    // throws IOException if an error occurs while reading file
    public JSONObject read() throws IOException, JSONException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return json;
    }

    // EFFECTS: takes in file source as string and returns string of content
    // Referenced from StackOverflow
    // https://stackoverflow.com/questions/7463414/what-s-the-best-way-to-load-a-jsonobject-from-a-json-text-file
    public String readFile(String source) throws IOException, JSONException {
        return new String(Files.readAllBytes(Paths.get(source)));
    }

    // EFFECTS: checks if source file is empty
    // true = not empty, false = empty
    public boolean checkFile() {
        try {
            read();
            return true;
        } catch (IOException e) {
            return false;
        } catch (JSONException e) {
            return false;
        }
    }

}
