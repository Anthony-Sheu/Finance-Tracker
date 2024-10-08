package ui;

import java.util.*;
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

    // EFFECTS: runs start application
    public Menu(){
        start();
    }

    // REQUIRES: 1 <= option <= 5
    // MODIFIES: this
    // EFFECTS: takes in user input
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

    // REQUIRES: 1 <= command <= 5
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

    // REQUIRES: 1 <= command <= 6
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
        System.out.println("=====================================================");
        System.out.println("What is the month of your transaction?");
        int month = Integer.parseInt(input.nextLine());
        System.out.println("What is the date of your transaction?");
        int date = Integer.parseInt(input.nextLine());
        System.out.println("What was the year of your transaction?");
        int year = Integer.parseInt(input.nextLine());
        System.out.println("What is the amount spent?");
        double amount = Double.parseDouble(input.nextLine());
        System.out.println("What is the name of the store/vendor?");
        String store = input.nextLine();
        System.out.println("What is the expense category?");
        String expense = input.nextLine();
        System.out.println("Any notes?");
        String note = input.nextLine();
        System.out.println("What is the account name?");
        String accountName = input.nextLine();
        System.out.println("What is the account type?");
        String accountType = input.nextLine();
        transaction = new Transaction(month, date, year, amount, store, expense, note, accountName, accountType);
        tracker.addTransaction(transaction);
        updateBank(accountName, accountType, -amount);
    }

    
    // EFFECTS: checks for account overdraft and warns the user
    public void checkOverdraft(Account account) {
        if (account.checkOverdraftChequeing()) {
            System.out.println("***WARNING***");
            System.out.println("Your "+account.getBank()+" chequeing account is overdrafted by $" + account.getChequeing());
        }
        if (account.checkOverdraftSavings()) {
            System.out.println("***WARNING***");
            System.out.println("Your "+account.getBank()+" savings account is overdrafted by $" + account.getSavings());
        }
        if (account.checkOverLimit()) {
            System.out.println("***WARNING***");
            System.out.println("Your "+account.getBank()+" credit account is overused by $" + (account.getCredit()-account.getCreditLimit()));
        }
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

    // REQUIRES: year, month >= 0
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

    // REQUIRES: amount >= 0, accName in bank and accType in bank
    // MODIFIES: this
    // EFFECTS: processes a refund by returning the amount back to
    // the proper account
    public void refund() {
        String accName;
        String accType;
        double amount;
        System.out.println("=====================================================");
        System.out.println("Which account would you like to refund to?");
        accName = input.nextLine();
        System.out.println("Which account type?");
        accType = input.nextLine();
        System.out.println("What is the amount you would like to refund today?");
        amount = Double.parseDouble(input.nextLine());
        account = bank.findAccount(accName);
        account.refund(accType, amount);
        System.out.println("Refunded!");
        enter();
    }

    // REQUIRES: 1 <= command <= 3
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

    // REQUIRES: amount <= 0
    // MODIFIES: this
    // EFFECTS: updates a bank account with new amount
    public void updateBank(String name, String acc, double amount) {
        account = bank.findAccount(name);
        if (acc.equals("Chequeing")) {
            account.updateChequeing(amount);
        } else if (acc.equals("Savings")) {
            account.updateSavings(amount);
        } else if (acc.equals("Credit")) {
            account.updateCredit(-amount);
        }
        System.out.println("Updated!");
        checkOverdraft(account);
        enter();
    }

    // REQUIRES: credLim > 0
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
        System.out.println("New bank account added!");
        enter();
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

    // REQUIRES: 1 <= command <= 3
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

    // REQUIRES: 0 <= amount <= account.getCredit()
    // MODIFIES: this
    // EFFECTS: pays credit card by specified amount
    public void payCreditCard() {
        String acc;
        double amount;
        System.out.println("=====================================================");
        System.out.println("What account would you like to pay?");
        acc = input.nextLine();
        System.out.println("How much would you like to pay?");
        amount = Double.parseDouble(input.nextLine());
        account = bank.findAccount(acc);
        account.updateCredit(-amount);
        System.out.println("Paid!");
        enter();
    }

    // REQUIRES: acc in bank, amount > 0
    // MODIFIES: this
    // EFFECTS: changes credit card limit on a certain credit card
    public void changeCreditLimit() {
        String acc;
        double amount;
        System.out.println("=====================================================");
        System.out.println("What account would you like edit?");
        acc = input.nextLine();
        System.out.println("How much is your new credit card limit?");
        amount = Double.parseDouble(input.nextLine());
        account = bank.findAccount(acc);
        account.updateCreditLimit(amount);
        System.out.println("Changed!");
        enter();
    }

    // REQUIRES: 1 <= command <= 2
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

    // REQUIRES: amount >= 0, acc1Type != acc2Type != "Credit"
    // MODIFIES: this
    // EFFECTS: moves a certain amount from one 
    // account to another
    public void transfer() {
        String acc1Name;
        String acc1Type;
        String acc2Name;
        String acc2Type;
        double amount;
        System.out.println("=====================================================");
        System.out.println("What is the first account you would like to transfer out of?");
        acc1Name = input.nextLine();
        System.out.println("What is the account type? (Credit is not allowed)");
        acc1Type = input.nextLine();
        System.out.println("What is the second account you would like to transfer into?");
        acc2Name = input.nextLine();
        System.out.println("What is the account type? (Credit is not allowed)");
        acc2Type = input.nextLine();
        System.out.println("What is the amount you would like to transfer?");
        amount = Double.parseDouble(input.nextLine());
        bank.transfer(acc1Name, acc1Type, acc2Name, acc2Type, amount);
        System.out.println("Transferred!");
        enter();
    }

    // EFFECTS: pauses console to let user read
    public void enter() {
        System.out.println("Press enter to continue");
        input.nextLine();
    }
    
}
