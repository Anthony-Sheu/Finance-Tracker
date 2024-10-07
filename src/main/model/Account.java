package model;

// Represents a bank account with bank name,chequeing, 
// savings, and credit accounts
public class Account {
    private String bank;
    private int chequeing;
    private int savings;
    private int credit;
    private boolean overdraftChequeing;
    private boolean overdraftSavings;
    private int creditLimit;
    private boolean overLimit;

    // REQUIRES: chequeing, savings, credit > 0
    // EFFECTS: constructs an account with chequeing, savings, and credit amounts
    public Account(int chequeing, int savings, int credit, String bank, int creditLimit) {
        this.chequeing = chequeing;
        this.savings = savings;
        this.credit = credit;
        this.overdraftChequeing = false;
        this.overdraftSavings = false;
        this.overLimit = false;
        this.bank = bank;
        this.creditLimit = creditLimit;
    }

    // MODIFIES: this
    // EFFECTS: returns true/false for chequeing overdraft
    public boolean checkOverdraftChequeing() {
        return overdraftChequeing;
    }

    // MODIFIES: this
    // EFFECTS: returns true/false for savings overdraft
    public boolean checkOverdraftSavings() {
        return overdraftSavings;
    }

    // MODIFIES: this
    // EFFECTS: returns true/false for credit over limit
    public boolean checkOverLimit() {
        return overLimit;
    }

    // MODIFES: this
    // EFFECTS: subtracts/adds credit account by Amount
    // and checks for overdraft
    public void updateCredit(int Amount) {
        this.credit += Amount;
        if (this.credit > creditLimit) {
            overLimit = true;
        } else {
            overLimit = false;
        }
    }   

    // MODIFES: this
    // EFFECTS: subtracts/adds savings account by Amount
    // and checks for overdraft
    public void updateSavings(int Amount) {
        this.savings += Amount;
        if (this.savings < 0) {
            overdraftSavings = true;
        } else {
            overdraftSavings = false;
        }
    }

    // MODIFES: this
    // EFFECTS: subtracts/adds chequeing account by Amount 
    // and checks for overdraft
    public void updateChequeing(int Amount) {
        this.chequeing += Amount;
        if (this.chequeing < 0) {
            overdraftChequeing = true;
        } else {
            overdraftChequeing = false;
        }
    }

    // EFFECTS: returns credit account balance
    public int getCredit() {
        return this.credit;
    }

    // EFFECTS: returns savings account balance
    public int getSavings() {
        return this.savings;
    }

    // EFFECTS: returns chequeing account balance
    public int getChequeing() {
        return this.chequeing;
    }

    //EFFECTS: returns bank name
    public String getBank() {
        return this.bank;
    }

    public String printAccount() {
        String cheq = Integer.toString(this.chequeing);
        String save = Integer.toString(this.savings);
        String cred = Integer.toString(this.credit);
        return bank+"\nChequeing: $"+cheq+"\nSavings: $"+save+"\nCredit: $"+cred;
    }

}
