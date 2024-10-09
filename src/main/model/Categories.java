package model;

import java.util.*;

// Represents a collection of expense categories
public class Categories {

    private List<Expense> category;

    // EFFECTS: constructs a colletcion of expense categories
    public Categories() {
        this.category = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds new expense category
    public void addCategory(Expense expense) {
        this.category.add(expense);
    }

    // MODIFIES: this
    // EFFECTS: removes certain expense category
    public void removeCategory(Expense expense) {
        this.category.remove(expense);
    }

    // EFFECTS: returns expense category based on its name
    public Expense checkCategory(String expense) {
        for (Expense e : category) {
            if (e.getExpense().equals(expense)) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: returns expenses
    public List<Expense> getExpense() {
        return category;
    }

}
