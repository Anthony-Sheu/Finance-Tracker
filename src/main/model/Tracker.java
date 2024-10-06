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
    public void addTransaction(int month, int date, int year, int amount, String store, Expense expense, String note, Account account) {
        Transaction transaction = new Transaction(month, date, year, amount, store, expense, note, account);
        this.tracker.add(transaction);
    }

    // MODIFIES: this
    // EFFECTS: removes an old transaction
    public void removeTransaction(Transaction transaction) {
        this.tracker.remove(transaction);
    }

    // MODIFIES: this
    // EFFECTS: finds transactions within a certain month
    public List<Transaction> sortDate(int month, int date, int year) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: finds transactions with a certain expense category
    public List<Transaction> sortExpense(Expense expense) {
        return null;
    }
}
