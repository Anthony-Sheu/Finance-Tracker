package model;

import java.util.*;

// Represents a collection of bank accounts
public class Banks {

    private List<Account> bank;

    // EFFECTS: constructs a list of bank accounts
    public Banks() {
        this.bank = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds new bank account to list
    public void newAccount(Account account) {
        this.bank.add(account);
    }

    // EFFCTS: returns account from account name
    public Account findAccount(String name) {
        for (Account account : this.bank) {
            if (account.getBank().equals(name)) {
                return account;
            }
        }
        return null;
    }

    // EFFECTS: returns chequeing value of a certain account
    public double getChequeing(Account account) {
        return account.getChequeing();
    }

    // EFFECTS: returns credit value of a certain account
    public double getCredit(Account account) {
        return account.getCredit();
    }

    // EFFECTS: returns savings value of a certain account
    public double getSavings(Account account) {
        return account.getSavings();
    }

    public List<Account> getBank() {
        return bank;
    }

}
