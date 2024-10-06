package model;

import java.util.*;

// Represents an expense category
public class Expense {

    private String expense;

    // EFFECTS: constructs a list of expenses with expense category names
    public Expense(String expense) {
        this.expense = expense;
    }

    // MODIFIES: this
    // EFFECTS: changes expense category name
    public void updateExpense(String expense) {
        this.expense = expense;
    }

    // EFFECTS: returns expense category name
    public String getExpense() {
        return this.expense;
    }


}
