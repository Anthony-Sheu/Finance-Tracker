package model;

// Represents a bank account with chequeing, savings, and credit accounts
public class Account {
    private int chequeing;
    private int savings;
    private int credit;

    // REQUIRES: chequeing, savings, credit > 0
    // EFFECTS: constructs an account with chequeing, savings, and credit amounts
    public Account(int chequeing, int savings, int credit) {
        this.chequeing = chequeing;
        this.savings = savings;
        this.credit = credit;
    }

    // MODIFES: this
    // EFFECTS: subtracts/adds credit account by Amount
    public void updateCredit(int Amount) {

    }

    // MODIFES: this
    // EFFECTS: subtracts/adds savings account by Amount
    public void updateSavings(int Amount) {

    }

    // MODIFES: this
    // EFFECTS: subtracts/adds chequeing account by Amount
    public void updateChequeing(int Amount) {

    }

    // EFFECTS: returns credit account balance
    public int getCredit() {
        return 0;
    }

    // EFFECTS: returns savings account balance
    public int getSavings() {
        return 0;
    }

    // EFFECTS: returns chequeing account balance
    public int getChequeing() {
        return 0;
    }

}
