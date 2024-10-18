package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads from JSON data in source file
public class JsonReader {

    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        
    }

    // EFFECTS: reads from source file and returns JSON object
    // throws IOException if an error occurs while reading file
    public JSONObject read() throws IOException {
        return null;
    }
}
