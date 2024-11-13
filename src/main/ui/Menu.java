package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.Account;
import model.Banks;
import model.Categories;
import model.Expense;
import model.Tracker;
import model.Transaction;
import persistence.BankReader;
import persistence.BankWriter;
import persistence.CategoriesReader;
import persistence.CategoriesWriter;
import persistence.TrackerReader;
import persistence.TrackerWriter;

public class Menu {

    protected static final String BANKS_STORE = "./data/Bank.json";
    protected static final String TRACKER_STORE = "./data/Tracker.json";
    protected static final String CATEGORIES_STORE = "./data/Categories.json";
    protected Scanner input;
    protected Banks bank;
    protected Account account;
    protected Tracker tracker;
    protected Transaction transaction;
    protected Categories category;
    protected Expense expense;
    protected BankWriter bankWriter;
    protected BankReader bankReader;
    protected TrackerWriter trackerWriter;
    protected TrackerReader trackerReader;
    protected CategoriesWriter categoryWriter;
    protected CategoriesReader categoryReader;

    public Menu() {

    }

    // MODIFIES: this
    // EFFECTS: initializes variables
    protected void init() {
        bank = new Banks();
        tracker = new Tracker();
        input = new Scanner(System.in);
        bankReader = new BankReader(BANKS_STORE);
        bankWriter = new BankWriter(BANKS_STORE);
        trackerReader = new TrackerReader(TRACKER_STORE);
        trackerWriter = new TrackerWriter(TRACKER_STORE);
        categoryReader = new CategoriesReader(CATEGORIES_STORE);
        categoryWriter = new CategoriesWriter(CATEGORIES_STORE);
        categoryInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes list of expense catgories
    public void categoryInit() {
        category = new Categories();
        category.addCategory(new Expense("Food"));
        category.addCategory(new Expense("Clothing"));
        category.addCategory(new Expense("Rent Fees"));
        category.addCategory(new Expense("Electronics"));
        category.addCategory(new Expense("Entertainment"));
        category.addCategory(new Expense("Car"));
        category.addCategory(new Expense("Gifts"));
        category.addCategory(new Expense("Groceries"));
        category.addCategory(new Expense("Gym"));
        category.addCategory(new Expense("Home Maintenance"));
        category.addCategory(new Expense("Public Transit"));
        category.addCategory(new Expense("Travel"));
        category.addCategory(new Expense("Going Out"));
        category.addCategory(new Expense("Work"));
    }

    // MODIFIES: this
    // EFFECTS: loads current banking information into json file
    protected void saveBankFile() throws FileNotFoundException {
        bankWriter.open();
        bankWriter.write(bankWriter.toJson(bank));
        bankWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: loads current transactions and categories information into json file
    protected void saveTrackerFile() throws FileNotFoundException {
        trackerWriter.open();
        trackerWriter.write(trackerWriter.toJson(tracker));
        trackerWriter.close();
        categoryWriter.open();
        categoryWriter.write(categoryWriter.toJson(category));
        categoryWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: loads bank information from saved json file
    protected void loadBankFile() throws IOException {
        bank = bankReader.toBanks(bankReader.read());
    }

    // MODIFIES: this
    // EFFECTS: checks if there is information in tracker file, and if there is, prompts
    // user if they would like to load it
    protected void checkTrackerFile() throws IOException {
        tracker = trackerReader.toTracker(trackerReader.read());
        category = categoryReader.toCategories(categoryReader.read());
    }

    // MODIFIES: this
    // EFFECTS: creates a new transaction and adds it to tracker, also updates 
    // banking accounts and expense category spendings
    protected void addTransaction(int m, int d, int y, double a, String s, String e, String n, String aN, String aT) {
        tracker.addTransaction(new Transaction(m, d, y, a, s, e, n, aN, aT));
        category.checkCategory(e).updateSpending(a);
    }

    // MODIFIES: this
    // EFFECTS: removes a transaction from tracker; refunds the amount to bank accounts; 
    // refunds amount to expense category; checks for overdraft. If there are not transactions, return to main
    // menu
    protected void removeTransaction(int line) {
        accountRefund(line);
        updateExpense();
        tracker.removeTransaction(transaction);
    }

    // REQUIRES: transaction is set to currently editing transaction
    // MODIFIES: this
    // EFFECTS: refunds a transaction amount back to account
    protected void accountRefund(int line) {
        transaction = tracker.getTracker().get(line);
        account = bank.findAccount(transaction.getAccountName());
        account.refund(transaction.getAccountType(), transaction.getAmount());
    }

    // REQUIRES: accountRefund is called beforehand
    // MODIFIES: this
    // EFFECTS: updates a specific expense category
    protected void updateExpense() {
        expense = category.checkCategory(transaction.getExpense());
        expense.updateSpending(-transaction.getAmount());
    }

    // MODIFIES: this
    // EFFECTS: creates a new account and adds it to the list bank
    protected void addBank(String name, double cheq, double save, double cred, double credLim) {
        account = new Account(cheq, save, cred, name, credLim);
        bank.newAccount(account);
    }

    // MODIFIES: this
    // EFFECTS: pays credit card by specified amount from another account
    protected void payCreditCard(String bank1, String bank2, String acc, double amount) {
        account = bank.findAccount(bank1);
        account.updateCredit(-amount);
        account = bank.findAccount(bank2);
        bank.updateTransfer(account, acc, -amount);
    }

    // MODIFIES: this
    // EFFECTS: changes credit card limit on a certain credit card
    protected void changeCreditLimit(String acc, double amount) {
        account = bank.findAccount(acc);
        account.updateCreditLimit(amount);
    }

    // MODIFIES: this
    // EFFECTS: moves a certain amount from one account to another
    protected void transfer(String acc1Name, String acc1Type, String acc2Name, String acc2Type, double amount) {
        bank.transfer(acc1Name, acc1Type, acc2Name, acc2Type, amount);
    }

}
