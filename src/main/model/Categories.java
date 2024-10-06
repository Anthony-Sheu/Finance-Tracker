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

    // EFFECTS: returns true if an expense category is 
    // within the collection, otherwise false
    public boolean checkCategory(Expense expense) {
        return this.category.contains(expense);
    }

}
