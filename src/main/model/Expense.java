package model;

import java.util.*;

// Represents an expense category
public class Expense {

    private String expense;
    private double spending;

    // EFFECTS: constructs a list of expenses with expense category names
    public Expense(String expense) {
        this.expense = expense;
        this.spending = 0;
    }

    // MODIFIES: this
    // EFFECTS: changes expense category name
    public void updateExpense(String expense) {
        this.expense = expense;
    }

    // GETTER
    public String getExpense() {
        return this.expense;
    }

    // GETTER
    public double getSpending() {
        return this.spending;
    }

    // MODIFIES: this
    // EFFECTS: updates spending
    public void updateSpending(double amount) {
        this.spending += amount;
    }


}
