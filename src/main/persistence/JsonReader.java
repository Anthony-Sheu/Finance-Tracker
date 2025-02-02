package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
    // Referenced from CPSC 210 - JsonSerializationDemo
    public String readFile(String source) throws IOException, JSONException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
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
