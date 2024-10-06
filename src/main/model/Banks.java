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
    // EFFECTS: creates a new bank account and adds 
    // it to the list
    public void newAccount(Account account) {
        this.bank.add(account);
    }

    // EFFCTS: returns account from account name
    public Account findAccount(String name) {
        for (Account account : bank) {
            if (account.getBank() == name) {
                return account;
            }
        }
        return null;
    }

    // EFFECTS: returns chequeing value of a certain account
    public int getChequeing(Account account) {
        return account.getChequeing();
    }

    // EFFECTS: returns credit value of a certain account
    public int getCredit(Account account) {
        return account.getCredit();
    }

    // EFFECTS: returns savings value of a certain account
    public int getSavings(Account account) {
        return account.getSavings();
    }

}
