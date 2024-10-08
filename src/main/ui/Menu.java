package ui;

import java.util.*;

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.IntegerConversion;
import org.junit.platform.engine.discovery.PackageNameFilter;

import model.Account;
import model.Banks;
import model.Categories;
import model.Expense;
import model.Tracker;
import model.Transaction;

// Represents a menu on the console application
public class Menu {

    private Scanner input;
    private Banks bank;
    private Account account;
    private Categories category;
    private Expense expense;
    private Tracker tracker;
    private Transaction transaction;

    //EFFECTS: runs start application
    public Menu(){
        start();
    }

    // MODIFIES: this
    //EFFECTS: takes in user input
    public void start() {
        int option = 0;
        init();
        while (true) {
            option = mainMenu();
            if (option == 5) {
                break;
            } else if (option == 1) {
                transactionsProcess();
            } else if (option == 2) {
                bankAccountsProcess();
            } else if (option == 3) {
                creditCardProcess();
            } else {
                transferProcess();
            }
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes and prompts user to add bank info
    public void init() {
        bank = new Banks();
        category = new Categories();
        tracker = new Tracker();
        input = new Scanner(System.in);
        System.out.println("======================START UP======================");
        System.out.println("Please start by entering your banking information");
        addBank();
    }

    // EFFECTS: prompts user input from main menu
    public int mainMenu() {
        System.out.println("======================MAIN MENU======================");
        System.out.println("What would you like to view/edit? (enter the number):");
        System.out.println("1. Transactions");
        System.out.println("2. Bank accounts");
        System.out.println("3. Credit card");
        System.out.println("4. Transfer between accounts");
        System.out.println("5. Quit");
        String command = input.nextLine();
        return Integer.parseInt(command);
    }

    // MODIFIES: this
    // EFFECTS: prompts user input from transactions menu
    public void transactionsProcess() {
        System.out.println("====================TRANSACTIONS=====================");
        System.out.println("What would you like to do? (enter the number)");
        System.out.println("1. Add a transaction");
        System.out.println("2. See all transactions");
        System.out.println("3. See all transactions in a given month/year");
        System.out.println("4. See all transactions by an expense category");
        System.out.println("5. Record a refund");
        System.out.println("6. Return to main menu");
        int command = Integer.parseInt(input.nextLine());
        if (command == 1) {
            addTransaction();
        } else if (command == 2) {
            showTransaction(tracker.getTracker());
        } else if (command == 3) {
            showMonthTransaction();
        } else if (command == 4) {
            showExpenseTransaction();
        } else if (command == 5) {
            refund();
        }
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: creates a new transaction and adds
    // it to the tracker 
    public void addTransaction() {
        String command;
        int month;
        int date;
        int year;
        Double amount;
        String store;
        String expense;
        String note;
        String account;
        String[] split;
        System.out.println("=====================================================");
        System.out.println("Please enter your transaction separated by commas (month, date, year, amount, store, expense category, note, account)");
        command = input.nextLine();
        split = command.split(",");
        month = Integer.parseInt(split[0]);
        date = Integer.parseInt(split[1]);
        year = Integer.parseInt(split[2]);
        amount = -Double.parseDouble(split[3]);
        store = split[4];
        expense = split[5];
        note = split[6];
        account = split[7];
        transaction = new Transaction(month, date, year, amount, store, expense, note, account);
        tracker.addTransaction(transaction);
        updateBank(account, amount);
    }

    // EFFECTS: prints out all transactions given
    public void showTransaction(List<Transaction> show) {
        System.out.println("=====================================================");
        if (show.size() == 0) {
            System.out.println("There are currently no transactions.");
        } else {
            for(int i = 0; i < show.size(); i++) {
                System.out.print(Integer.toString(i+1)+". ");
                System.out.println(show.get(i).printTransaction());
            }
        }
        enter();
    }

    // EFFECTS: finds all transactions under an expense category 
    // and prints it
    public void showExpenseTransaction() {
        System.out.println("=====================================================");
        System.out.println("What expense category would you like to view?");
        String expense = input.nextLine();
        showTransaction(tracker.sortExpense(expense));
    }

    // EFFECTS: finds all transactions within a certain month/year
    // and prints it
    public void showMonthTransaction() {
        System.out.println("=====================================================");
        System.out.println("What year would you like to view?");
        int year = Integer.parseInt(input.nextLine());
        System.out.println("What month (in numbers)?");
        int month = Integer.parseInt(input.nextLine());
        showTransaction(tracker.sortMonth(month, year));
    }

    // MODIFIES: this
    // EFFECTS: processes a refund by returning the amount back to
    // the proper account
    public void refund() {
        System.out.println("=====================================================");
    }

    // MODIFIES: this
    // EFFECTS: prompts user input in bank accounts menu
    public void bankAccountsProcess() {
        System.out.println("====================BANK ACCOUNTS====================");
        System.out.println("What would you like to do? (enter the number)");
        System.out.println("1. Add new bank account");
        System.out.println("2. See bank account balances");
        System.out.println("3. Return to main menu");
        int command = Integer.parseInt(input.nextLine());
        if (command == 1) {
            addBank();
        } else if (command == 2) {
            showBank();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates a bank account with new amount
    public void updateBank(String info, double amount) {
        String[] accountSplit = info.split(" ");
        String name = accountSplit[0];
        String acc = accountSplit[1];
        account = bank.findAccount(name);
        if (acc.equals("Chequeing")) {
            account.updateChequeing(amount);
        } else if (acc.equals("Savings")) {
            account.updateSavings(amount);
        } else if (acc.equals("Credit")) {
            account.updateCredit(amount);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new bank account
    public void addBank() {
        System.out.println("=====================================================");
        System.out.println("What is the name of your banking institution?");
        String name = input.nextLine();
        System.out.println("Please enter your chequeing balance");
        double cheq = Double.parseDouble(input.nextLine());
        System.out.println("Please enter your savings balance");
        double save = Double.parseDouble(input.nextLine());
        System.out.println("Please enter your credit balance");
        double cred = Double.parseDouble(input.nextLine());
        System.out.println("Please enter your credit limit");
        double credLim = Double.parseDouble(input.nextLine());
        account = new Account(cheq, save, cred, name, credLim);
        bank.newAccount(account);
    }

    // EFFECTS: prints out all banking information
    public void showBank() {
        System.out.println("=====================================================");
        System.out.println("Your banking information");
        for(Account acc : bank.getBank()) {
            System.out.println(acc.printAccount());
            System.out.println();
        }
        enter();
    }

    // MODIFIES: this
    // EFFECTS: prompts user input from credit cards menu
    public void creditCardProcess() {
        System.out.println("====================CREDIT CARDS=====================");
        System.out.println("What would you like to do? (enter the number)");
        System.out.println("1. Pay credit card");
        System.out.println("2. Change credit card limit");
        System.out.println("3. Return to main menu");
        int command = Integer.parseInt(input.nextLine());
        if (command == 1) {
            payCreditCard();
        } else if (command == 2) {
            changeCreditLimit();
        }
    }

    // MODIFIES: this
    // EFFECTS: pays credit card by specified amount
    public void payCreditCard() {}

    // MODIFIES: this
    // EFFECTS: changes credit card limit on a certain credit card
    public void changeCreditLimit() {}

    // MODIFIES: this
    // EFFECTS: prompts user input from transfer menu
    public void transferProcess() {
        System.out.println("======================TRANSFER=======================");
        System.out.println("1. Transfer between accounts");
        System.out.println("2. return to main menu");
        int command = Integer.parseInt(input.nextLine());
        if (command == 1) {
            transfer();
        }

    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: moves a certain amount from one 
    // account to another
    public void transfer() {}

    // EFFECTS: pauses console to let user read
    public void enter() {
        System.out.println("Press enter to continue");
        input.nextLine();
    }
    
}
