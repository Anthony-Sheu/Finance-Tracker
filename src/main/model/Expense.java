package model;


// Represents an expense category
public class Expense {

    private String expense;
    private double spending;

    // EFFECTS: constructs a list of expenses with expense category names
    public Expense(String expense) {
        this.expense = expense;
        this.spending = 0;
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
