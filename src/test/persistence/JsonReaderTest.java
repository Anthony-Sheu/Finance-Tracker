package persistence;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.jar.JarEntry;

public class JsonReaderTest {

    @Test
    protected void testReaderNonExistentFile(String source) {
        JsonReader reader = new JsonReader(source);
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


}
