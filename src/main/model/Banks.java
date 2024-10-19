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

    // EFFECTS: returns list of bank accounts
    public List<Account> getBank() {
        return bank;
    }

    // MODIFIES: this
    // EFFECTS: updates a certain account of a banking account
    public void updateTransfer(Account account, String type, double amount) {
        if (type.equals("Chequeing")) {
            account.updateChequeing(amount);
        } else if (type.equals("Savings")) {
            account.updateSavings(amount);
        } else {
            account.updateCredit(amount);
        }
    }

    // REQUIRES: account1Name and account2Name to be valid bank accounts
    // MODIFIES: this
    // EFFECTS: performs a transaction between two accounts from account1 to account2
    public void transfer(String acc1Name, String acc1Type, String acc2Name, String acc2Type, double amount) {
        Account account1 = findAccount(acc1Name);
        Account account2 = findAccount(acc2Name);
        updateTransfer(account1, acc1Type, -amount);
        updateTransfer(account2, acc2Type, amount);
    }

}
