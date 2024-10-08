package model;

import java.util.*;

// Represents a collection of transactions
public class Tracker {

    private List<Transaction> tracker;

    // EFFECTS: constructs a list of transactions
    public Tracker() {
        this.tracker = new ArrayList<>();
    }

    // EFFECTS: returns list of all transactions
    public List<Transaction> getTracker() {
        return tracker;
    }

    // MODIFIES: this
    // EFFECTS: adds a new transaction to the collection
    public void addTransaction(Transaction transaction) {
        this.tracker.add(transaction);
    }

    // MODIFIES: this
    // EFFECTS: removes an old transaction
    public void removeTransaction(Transaction transaction) {
        this.tracker.remove(transaction);
    }

    // MODIFIES: this
    // EFFECTS: finds transactions within a certain month
    public List<Transaction> sortMonth(int month, int year) {
        List<Transaction> sortedTransaction = new ArrayList<>();
        for (Transaction t : tracker) {
            if (t.getMonth() == month && t.getYear() == year) {
                sortedTransaction.add(t);
            }
        }
        return sortedTransaction;
    }

    // MODIFIES: this
    // EFFECTS: finds transactions with a certain expense category
    public List<Transaction> sortExpense(String expense) {
        List<Transaction> sortedTransaction = new ArrayList<>();
        for (Transaction t : tracker) {
            if (t.getExpense().equals(expense)) {
                sortedTransaction.add(t);
            }
        }
        return sortedTransaction;
    }

    // EFFECTS: returns the transaction at a certain index
    public Transaction findTransaction(int index) {
        return tracker.get(index);
    }

    // MODIFIES: this
    // EFFECTS: modifies the expense category of a specific 
    // transaction
    public void updateExpense(int index, String expense) {
        findTransaction(index).updateExpense(expense);
    }

}
