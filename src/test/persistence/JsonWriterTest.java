package persistence;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testReadInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/\0rainbow.json");
            writer.open();
            fail("IOException expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

}
