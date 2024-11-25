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
    public Transaction(int m, int d, int y, double amt, String s, String e, String n, String an, String at) {
        this.month = m;
        this.date = d;
        this.year = y;
        this.amount = amt;
        this.store = s;
        this.expense = e;
        if (n.equals("")) {
            this.note = "No note";
        } else {
            this.note = n;
        }
        this.accountName = an;
        this.accountType = at;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: updates the current transaction's amount
    public void updateAmount(double amount) {
        this.amount = amount;
        EventLog.getInstance().logEvent(
            new Event("Updated old transaction amount in tracker list")
        );
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
    public int getDate() {
        return this.date;
    }

    // GETTER
    public String getStore() {
        return this.store;
    }

    // GETTER
    public String getExpense() {
        return this.expense;
    }

    // GETTER
    public String getNote() {
        return this.note;
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

    // EFFECTS: returns string of transaction information
    public String printTransaction() {
        String m = Integer.toString(month) + "/";
        String d = Integer.toString(date) + "/";
        String y = Integer.toString(year) + ", $";
        String a = Double.toString(amount) + ", ";
        return m + d + y + a + store + ", " + expense + ", " + note + ", " + accountName + " " + accountType;
    }

}
