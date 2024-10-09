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
    private String accountName;
    private String accountType;

    // EFFECTS: constructs a single transaction
    public Transaction(int month, int date, int year, double amount, String store, String expense, String note, String accountName, String accountType) {
        this.month = month;
        this.date = date;
        this.year = year;
        this.amount = amount;
        this.store = store;
        this.expense = expense;
        if (note.equals("")) {
            this.note = "No note";
        } else {
            this.note = note;
        }
        this.accountName = accountName;
        this.accountType = accountType;
    }

    public void updateExpense(String expense) {
        this.expense = expense;
    }

    // GETTER
    public int getMonth() {
        return this.month;
    }

    // GETTER
    public int getYear() {
        return this.year;
    }

    // GETTER
    public String getExpense() {
        return this.expense;
    }

    // GETTER
    public String getAccountName() {
        return this.accountName;
    }

    // GETTER
    public String getAccountType() {
        return this.accountType;
    }

    // GETTER
    public double getAmount() {
        return this.amount;
    }

    public String printTransaction() {
        String m = Integer.toString(month) + "/";
        String d = Integer.toString(date) + "/";
        String y = Integer.toString(year) + ", $";
        String a = Double.toString(amount) + ", ";
        return m + d + y + a + store + ", " + expense + ", " + note + ", " + accountName + " " + accountType;
    }

}
