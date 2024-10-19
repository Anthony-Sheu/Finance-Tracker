package persistence;

import model.Tracker;
import model.Transaction;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class TrackerPersistenceTest {

    private String source;
    private Tracker tracker;
    private Transaction t1, t2;
    private TrackerReader reader, readerEmpty;
    private TrackerWriter writer;

    @BeforeEach
    void runBefore() {
        source = "./data/Tracker.json";
        writer = new TrackerWriter(source);
        reader = new TrackerReader(source);
        readerEmpty = new TrackerReader("./data/TrackerTestEmpty.json");
        tracker = new Tracker();
        t1 = new Transaction(9, 18, 2024, 100, "Uber", "Transportation", "", "CIBC", "Chequeing");
        t2 = new Transaction(8, 21, 2023, 5, "McDonald's", "Food", "", "RBC", "Credit");
    }

    @Test
    void testReadEmptyFile() {
        try {
            tracker = readerEmpty.toTracker(readerEmpty.read());
            fail("Error expected");
        } catch (IOException e) {
            // pass
        } catch (JSONException e) {
            // pass
        }
    }

    @Test
    void testWriteNewTracker() {
        try {
            tracker.addTransaction(t1);
            writer.open();
            writer.write(writer.toJson(tracker));
            writer.close();
            tracker = reader.toTracker(reader.read());
            checkTransaction(t1, tracker.findTransaction(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteTwoTracker() {
        try {
            tracker.addTransaction(t1);
            tracker.addTransaction(t2);
            writer.open();
            writer.write(writer.toJson(tracker));
            writer.close();
            tracker = reader.toTracker(reader.read());
            checkTransaction(t1, tracker.findTransaction(0));
            checkTransaction(t2, tracker.findTransaction(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void checkTransaction(Transaction t1, Transaction t2) {
        assertEquals(t1.getAccountName(), t2.getAccountName());
        assertEquals(t1.getMonth(), t2.getMonth());
        assertEquals(t1.getYear(), t2.getYear());
        assertEquals(t1.getExpense(), t2.getExpense());
        assertEquals(t1.getAccountType(), t2.getAccountType());
        assertEquals(t1.getAmount(), t2.getAmount());
        assertEquals(t1.getStore(), t2.getStore());
        assertEquals(t1.getNote(), t2.getNote());
        assertEquals(t1.getDate(), t2.getDate());
    }

}
