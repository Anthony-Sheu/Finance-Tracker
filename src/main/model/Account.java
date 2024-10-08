package model;

// Represents a bank account with bank name,chequeing, 
// savings, and credit accounts
public class Account {
    private String bank;
    private double chequeing;
    private double savings;
    private double credit;
    private boolean overdraftChequeing;
    private boolean overdraftSavings;
    private double creditLimit;
    private boolean overLimit;

    // REQUIRES: chequeing, savings, credit > 0
    // EFFECTS: constructs an account with chequeing, savings, and credit amounts
    public Account(double chequeing, double savings, double credit, String bank, double creditLimit) {
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
    // EFFECTS: adds credit account by amount
    // and checks for overdraft
    public void updateCredit(double amount) {
        this.credit += amount;
        if (this.credit > creditLimit) {
            overLimit = true;
            double difference = getCredit() - getCreditLimit();
            System.out.println("***WARNING***");
            System.out.println("Your " + getBank() + " credit account is overused by $" + difference);
        } else {
            
            overLimit = false;
        }
    }   

    // MODIFIES: this
    // EFFECTS: updates credit card limit
    public void updateCreditLimit(double amount) {
        this.creditLimit = amount;
        if (this.credit > creditLimit) {
            overLimit = true;
            double difference = getCredit() - getCreditLimit();
            System.out.println("***WARNING***");
            System.out.println("Your " + getBank() + " credit account is overused by $" + difference);
        } else {
            overLimit = false;
        }
    }

    // MODIFES: this
    // EFFECTS: adds savings account by amount
    // and checks for overdraft
    public void updateSavings(double amount) {
        this.savings += amount;
        if (this.savings < 0) {
            overdraftSavings = true;
            System.out.println("***WARNING***");
            System.out.println("Your " + getBank() + " savings account is overdrafted by $" + getSavings());
        } else {
            overdraftSavings = false;
        }
    }

    // MODIFES: this
    // EFFECTS: adds chequeing account by amount 
    // and checks for overdraft
    public void updateChequeing(double amount) {
        this.chequeing += amount;
        if (this.chequeing < 0) {
            overdraftChequeing = true;
            System.out.println("***WARNING***");
            System.out.println("Your " + getBank() + " chequeing account is overdrafted by $" + getChequeing());
        } else {
            overdraftChequeing = false;
        }
    }

    // EFFECTS: returns credit account balance
    public double getCredit() {
        return this.credit;
    }

    // EFFECTS: returns savings account balance
    public double getSavings() {
        return this.savings;
    }

    // EFFECTS: returns chequeing account balance
    public double getChequeing() {
        return this.chequeing;
    }

    // EFFECTS: returns bank name
    public String getBank() {
        return this.bank;
    }

    // EFFECTS: returns credit card limit
    public double getCreditLimit() {
        return this.creditLimit;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: refunds amount to accType, chequeing and savings
    // gain amount while credit loses amount
    public void refund(String accType, double amount) {
        if (accType.equals("Chequeing")) {
            updateChequeing(amount);;
        } else if (accType.equals("Savings")) {
            updateSavings(amount);
        } else {
            updateCredit(-amount);
        }
    }

    // EFFECTS: prints banking information
    public String printAccount() {
        String cheq = "\nChequeing: $" + Double.toString(this.chequeing);
        String save = "\nSavings: $" + Double.toString(this.savings);
        String cred = "\nCredit: $" + Double.toString(this.credit);
        String credLim = "\nCredit Limit: $" + Double.toString(this.creditLimit);
        return bank + cheq +  save +  cred +  credLim;
    }
}
