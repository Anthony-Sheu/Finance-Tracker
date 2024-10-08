package model;

// Represents a single transaction with date, amount, 
// store, expense category, notes, account, and spend type
public class Transaction {

    private int month;
    private int date;
    private int year;
    private double amount;
    private String store;
    private String expense;
    private String note;
    private String account;
    private boolean type;  // true = income, false = spending

    // EFFECTS: constructs a single transaction
    public Transaction(int month, int date, int year, double amount, String store, String expense, String note, String account) {
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

    public void updateExpense(String expense) {
        this.expense = expense;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public String getExpense() {
        return this.expense;
    }

    public boolean getType() {
        return type;
    }

    public String printTransaction() {
        String m = Integer.toString(month);
        String d = Integer.toString(date);
        String y = Integer.toString(year);
        String a = Double.toString(amount);
        return m+"/"+d+"/"+y+", $"+a+", "+store+", "+expense+", "+note+", "+account;
    }

}
