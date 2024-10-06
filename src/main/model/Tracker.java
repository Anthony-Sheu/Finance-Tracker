package model;

import java.util.*;

// Represents a collection of transactions
public class Tracker {

    private List<Transaction> tracker;

    // EFFECTS: constructs a list of transactions
    public Tracker() {
        this.tracker = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new transaction to the collection
    public void addTransaction(String month, String date, String year, int amount, String store, Expense expense, String note, Account account) {
        
    }

    // MODIFIES: this
    // EFFECTS: removes an old transaction
    public void removeTransaction(Transaction transaction) {

    }
}
