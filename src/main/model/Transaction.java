package model;

// Represents a single transaction with date, amount, 
// store, expense category, notes, account, and spend type
public class Transaction {

    private String month;
    private String date;
    private String year;
    private int amount;
    private String store;
    private Expense expense;
    private String note;
    private Account account;
    private boolean type;  // true = income, false = spending

    // EFFECTS: constructs a single transaction
    public Transaction(String month, String date, String year, int amount, String store, Expense expense, String note, Account account) {
        this.month = month;
        this.date = date;
        this.year = year;
        this.amount = amount;
        this.store = store;
        this.expense = expense;
        this.note = note;
        this.account = account;
        if(amount < 0) {
            type = true;
        } else {
            type = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the date of a transaction
    public void updateDate(String month, String date, String year) {
        
    }

    // MODIFIES: this
    // EFFECTS: updates the amount of a transaction
    public void updateAmount(int amount) {

    }

    // MODIFIES: this
    // EFFECTS: changes the expense category
    public void updateExpense(Expense expense) {

    }

}
