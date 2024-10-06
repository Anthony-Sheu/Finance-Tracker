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
    public void newAccount(int chequeing, int savings, int credit, String bank) {
        Account account = new Account(chequeing, savings, credit, bank);
        this.bank.add(account);
    }

}
