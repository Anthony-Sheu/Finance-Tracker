package persistence;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import netscape.javascript.JSException;

import java.io.IOException;
import java.util.jar.JarEntry;

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/rainbow.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReadEmptyFile() {
        JsonReader reader = new JsonReader("./data/Empty.json");
        assertFalse(reader.checkFile());
    }

    @Test
    public void testReadFile() {
        JsonReader reader = new JsonReader("./data/CorrectFile.json");
        assertTrue(reader.checkFile());
    }

    @Test
    public void testReadErrorFile() {
        JsonReader reader = new JsonReader("./data/ErrorFil.json");
        assertFalse(reader.checkFile());
    }

}
