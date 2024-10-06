package model;

import java.util.*;

// Represents a list of expense categories
public class Expense {

    private List<String> expense;

    // EFFECTS: constructs a list of expenses with expense category names
    public Expense() {
        this.expense = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds new expense category
    private void addExpense(String expense) {
        
    }

    // MODIFIES: this
    // EFFECTS: removes a given expense from the list
    private void removeExpense(String expense) {

    }

    // EFFECTS: returns true if given expense category is a category,
    // otherwise false
    private boolean hasExpense(String expense) {
        return false;
    }


}
