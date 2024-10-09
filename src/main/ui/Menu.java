package ui;

import java.util.*;

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
    private Tracker tracker;
    private Transaction transaction;
    private Categories category;
    private Expense expense;

    // EFFECTS: runs start application
    public Menu() {
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
                transactionsPrint();
            } else if (option == 2) {
                bankAccountsProcess();
            } else if (option == 3) {
                creditCardProcess();
            } else if (option == 4) {
                transferProcess();
            }
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes and prompts user to add bank info
    public void init() {
        bank = new Banks();
        tracker = new Tracker();
        input = new Scanner(System.in);
        categoryInit();
        System.out.println("======================START UP======================");
        System.out.println("Please start by entering your banking information");
        addBank();
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

    // REQUIRES: 1 <= command <= 5
    // EFFECTS: prompts user input from main menu
    public int mainMenu() {
        System.out.println("======================MAIN MENU======================");
        System.out.println("What would you like to do? (enter the number):");
        System.out.println("1. Transactions");
        System.out.println("2. Bank accounts");
        System.out.println("3. Credit card");
        System.out.println("4. Transfer between accounts");
        System.out.println("5. Quit");
        return intInput();
    }

    // REQUIRES: 1 <= command <= 6
    // MODIFIES: this
    // EFFECTS: prompts user input from transactions menu
    public void transactionsPrint() {
        System.out.println("====================TRANSACTIONS=====================");
        System.out.println("What would you like to do? (enter the number)");
        System.out.println("1. Add a transaction");
        System.out.println("2. Remove a transaction");
        System.out.println("3. Edit a previous transaction");
        System.out.println("4. See all transactions");
        System.out.println("5. See all transactions in a given month/year");
        System.out.println("6. See all transactions by an expense category");
        System.out.println("7. Show spending in each expense category");
        System.out.println("8. Record a refund");
        System.out.println("9. Return to main menu");
        transactionProcess();
    }

    // EFFECTS: directs user to correct transaction menu
    public void transactionProcess() {
        int command = intInput();
        if (command == 1) {
            addTransaction();
        } else if (command == 2) {
            removeTransaction();
        } else if (command == 3) {
            editTransaction();           
        } else if (command == 4) {
            showTransaction(tracker.getTracker());            
        } else if (command == 5) {
            showMonthTransaction();         
        } else if (command == 6) {
            showExpenseTransaction();
        } else if (command == 7) {
            showExpense();
        } else if (command == 8) {
            refund();
        }
    }

    // REQUIRES: amount >= 0; accountName = account.getBank(); accountType is of 
    // Chequeing, Savings, or Credit; month, date, year > 0; store is String;
    // category.checkCategory(exp) != null; note is String
    // MODIFIES: this
    // EFFECTS: creates a new transaction and adds it to tracker, also updates 
    // banking accounts and expense category spendings
    @SuppressWarnings("methodlength")
    public void addTransaction() {
        System.out.println("=====================================================");
        System.out.println("What is the number month of your transaction?");
        int month = intInput();
        System.out.println("What is the date of your transaction?");
        int date = intInput();
        System.out.println("What was the year of your transaction?");
        int year = intInput();
        System.out.println("What is the amount spent?");
        double amount = doubleInput();
        System.out.println("What is the name of the store/vendor?");
        String store = stringInput();
        System.out.println("What is the expense category?");
        printExpenseCategories();
        String exp = checkExpense();
        System.out.println("Any notes? (press enter to skip)");
        String note = stringInput();
        System.out.println("What is the account name?");
        printAccountNames();
        String accountName = checkBank();
        System.out.println("What is the account type?\nChequeing, Savings, Credit");
        String accountType = checkAccount();
        tracker.addTransaction(new Transaction(month, date, year, amount, store, exp, note, accountName, accountType));
        updateBank(accountName, accountType, -amount);
        category.checkCategory(exp).updateSpending(amount);
    }

    // REQUIRES: 0 < line <= tracker.getTracker.size() and tracker.getTracker.size() > 0
    // MODIFIES: this
    // EFFECTS: removes a transaction from tracker; refunds the amount to bank accounts; 
    // refunds amount to expense category; checks for overdraft. If there are not transactions, return to main
    // menu
    public void removeTransaction() {
        if (tracker.getTracker().size() != 0) {
            System.out.println("=====================================================");
            System.out.println("Here are your transactions:");
            showTransaction(tracker.getTracker());
            System.out.println("Enter the line number of the transaction you wish to delete");
            int line = intInput() - 1;
            transaction = tracker.getTracker().get(line);
            account = bank.findAccount(transaction.getAccountName());
            account.refund(transaction.getAccountType(), transaction.getAmount());
            checkOverdraft(account);
            expense = category.checkCategory(transaction.getExpense());
            expense.updateSpending(-transaction.getAmount());
            tracker.removeTransaction(transaction);
            System.out.println("Deleted!");
            enter();
        } else {
            showTransaction(tracker.getTracker());
        }
    }

    // REQUIRES: 0 < line <= tracker.getTracker().size()
    // MODIFIES: this
    // EFFECTS: edits a previous transaction
    public void editTransaction() {
        if (tracker.getTracker().size() != 0) {
            System.out.println("=====================================================");
            System.out.println("Here are your transactions:");
            showTransaction(tracker.getTracker());
            System.out.println("Enter the line number of the transaction you wish to edit");
            int line = intInput() - 1;
            transaction = tracker.getTracker().get(line);
            account = bank.findAccount(transaction.getAccountName());
            account.refund(transaction.getAccountType(), transaction.getAmount());
            System.out.println("What is the new amount?");
            double amount = doubleInput();
            updateBank(account.getBank(), transaction.getAccountType(), -amount);
            expense = category.checkCategory(transaction.getExpense());
            expense.updateSpending(-transaction.getAmount());
            expense.updateSpending(amount);
            transaction.updateAmount(amount);
        } else {
            showTransaction(tracker.getTracker());
        }
    }

    // EFFECTS: prints out all transactions in show
    public void showTransaction(List<Transaction> show) {
        System.out.println("=====================================================");
        if (show.size() == 0) {
            System.out.println("There are currently no transactions.");
        } else {
            for (int i = 0; i < show.size(); i++) {
                String s = ". ";
                System.out.print(Integer.toString(i + 1) + s);
                System.out.println(show.get(i).printTransaction());
            }
        }
        enter();
    }

    // EFFECTS: finds all transactions under an expense category and prints it
    public void showExpenseTransaction() {
        System.out.println("=====================================================");
        System.out.println("What expense category would you like to view?");
        String expense = checkExpense();
        showTransaction(tracker.sortExpense(expense));
    }

    // REQUIRES: year, month > 0
    // EFFECTS: finds all transactions within a certain month/year and prints it
    public void showMonthTransaction() {
        System.out.println("=====================================================");
        System.out.println("What year would you like to view?");
        int year = intInput();
        System.out.println("What month (in numbers)?");
        int month = intInput();
        showTransaction(tracker.sortMonth(month, year));
    }

    // EFFECTS: prints spendings in each expense category by increasing line number
    public void showExpense() {
        System.out.println("=====================================================");
        List<Expense> c = category.getExpense();
        System.out.println("Here are your expense spendings:");
        for (int i = 0; i < c.size(); i++) {
            Expense e = c.get(i);
            String name = ". " + e.getExpense();
            String spend = ": $" + Double.toString(e.getSpending());
            System.out.println(Integer.toString(i + 1) + name + spend);
        }
        enter();
    }

    // REQUIRES: amount >= 0, bank.findAccount(accName) != null, 0 < line <= tracker.getTracker.size()
    // MODIFIES: this
    // EFFECTS: processes a refund by returning the amount back to the proper account, checking 
    // for overdraft, refunding to the proper expense category, and removing the transaction 
    // from tracker
    public void refund() {
        if (tracker.getTracker().size() != 0) {
            System.out.println("=====================================================");
            System.out.println("Here are your transactions:");
            showTransaction(tracker.getTracker());
            System.out.println("Which transaction would you like to refund? (enter the line number)");
            int line = intInput() - 1;
            transaction = tracker.findTransaction(line);
            String accName = transaction.getAccountName();
            String accType = transaction.getAccountType();
            String exp = transaction.getExpense();
            double amount = transaction.getAmount();
            account = bank.findAccount(accName);
            account.refund(accType, amount);
            checkOverdraft(account);
            expense = category.checkCategory(exp);
            expense.updateSpending(-amount);
            tracker.removeTransaction(transaction);
            System.out.println("Refunded!");
            enter();
        } else {
            showTransaction(tracker.getTracker());
        }
    }

    // EFFECT: prints all current bank institutions
    public void printAccountNames() {
        String s = "Your current account names are:\n";
        for (Account a : bank.getBank()) {
            s += a.getBank() + " ";
        }
        System.out.println(s);
    }

    // EFFECT: prints all current expense categories
    public void printExpenseCategories() {
        String s = "Your current expense categories are:\n";
        Expense e;
        List<Expense> c = category.getExpense();
        for (int i = 0; i < c.size(); i++) {
            e = c.get(i);
            if (i != c.size() - 1) {
                s += e.getExpense() + ", ";
            } else {
                s += e.getExpense();
            }
        }
        System.out.println(s);
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
        int command = intInput();
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
        checkOverdraft(account);
        System.out.println("Updated!");
        enter();
    }

    // EFFECTS: checks for account overdraft after a transaction
    public void checkOverdraft(Account account) {
        String s = account.getBank();
        if (account.checkOverdraftChequeing()) {
            System.out.println("***WARNING***");
            System.out.println("Your " + s + " chequeing account is overdrafted by $" + account.getChequeing());
        }
        if (account.checkOverdraftSavings()) {
            System.out.println("***WARNING***");
            System.out.println("Your " + s + " savings account is overdrafted by $" + account.getSavings());
        }
        if (account.checkOverLimit()) {
            double difference = account.getCredit() - account.getCreditLimit();
            System.out.println("***WARNING***");
            System.out.println("Your " + s + " credit account is overused by $" + difference);
        }
    }

    // REQUIRES: credLim > 0
    // MODIFIES: this
    // EFFECTS: adds a new bank account
    public void addBank() {
        System.out.println("=====================================================");
        System.out.println("What is the name of your banking institution?");
        String name = stringInput();
        System.out.println("Please enter your chequeing balance");
        double cheq = doubleInput();
        System.out.println("Please enter your savings balance");
        double save = doubleInput();
        System.out.println("Please enter your credit balance");
        double cred = doubleInput();
        System.out.println("Please enter your credit limit");
        double credLim = doubleInput();
        account = new Account(cheq, save, cred, name, credLim);
        bank.newAccount(account);
        System.out.println("New bank account added!");
        enter();
    }

    // EFFECTS: prints out all banking information
    public void showBank() {
        System.out.println("=====================================================");
        System.out.println("Your banking information");
        for (Account acc : bank.getBank()) {
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
        int command = intInput();
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
        acc = checkBank();
        System.out.println("How much would you like to pay?");
        amount = doubleInput();
        account = bank.findAccount(acc);
        account.updateCredit(-amount);
        System.out.println("Paid!");
        enter();
    }

    // REQUIRES: bank.findAccount(acc) != null, amount > 0
    // MODIFIES: this
    // EFFECTS: changes credit card limit on a certain credit card and checks
    // for credit account over-usage
    public void changeCreditLimit() {
        String acc;
        double amount;
        System.out.println("=====================================================");
        System.out.println("What account would you like edit?");
        acc = checkBank();
        System.out.println("How much is your new credit card limit?");
        amount = doubleInput();
        account = bank.findAccount(acc);
        account.updateCreditLimit(amount);
        System.out.println("Changed!");
        checkOverdraft(account);
        enter();
    }

    // REQUIRES: 1 <= command <= 2
    // MODIFIES: this
    // EFFECTS: prompts user input from transfer menu
    public void transferProcess() {
        System.out.println("======================TRANSFER=======================");
        System.out.println("1. Transfer between accounts");
        System.out.println("2. return to main menu");
        int command = intInput();
        if (command == 1) {
            transfer();
        }

    }

    // REQUIRES: amount >= 0, acc1Type != acc2Type != "Credit"
    // MODIFIES: this
    // EFFECTS: moves a certain amount from one account to another
    public void transfer() {
        double amount;
        System.out.println("=====================================================");
        System.out.println("What is the first account you would like to transfer out of?");
        String acc1Name = checkBank();
        System.out.println("What is the account type? (Credit is not allowed)");
        String acc1Type = checkAccount();
        System.out.println("What is the second account you would like to transfer into?");
        String acc2Name = checkBank();
        System.out.println("What is the account type? (Credit is not allowed)");
        String acc2Type = checkAccount();
        System.out.println("What is the amount you would like to transfer?");
        amount = doubleInput();
        bank.transfer(acc1Name, acc1Type, acc2Name, acc2Type, amount);
        checkOverdraft(bank.findAccount(acc1Name));
        if (!acc1Name.equals(acc2Name)) {
            checkOverdraft(bank.findAccount(acc2Name));
        }
        System.out.println("Transferred!");
        enter();
    }

    // EFFECTS: pauses console to let user read
    public void enter() {
        System.out.println("Press enter to continue");
        input.nextLine();
    }

    // EFFECTS: checks to make sure user input is a positive double
    public double doubleInput() {
        String in;
        double inTry;
        while (true) {
            in = input.nextLine();
            try {
                inTry = Double.parseDouble(in);
                if (inTry >= 0) {
                    return inTry;
                } else {
                    System.out.println("Please enter a positive decimal amount");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decmial amount");
            }
        }
    }
    
    // EFFECTS: checks to make sure user input is of form String
    public String stringInput() {
        String in;
        while (true) {
            in = input.nextLine();
            try {
                Double.parseDouble(in);
                System.out.println("Please enter a string");
            } catch (NumberFormatException e) {
                return in;
            }
        }
    }

    // EFFECTS: checks to make sure user input is a positive integer
    public int intInput() {
        String in;
        int inTry;
        while (true) {
            in = input.nextLine();
            try {
                inTry = Integer.parseInt(in);
                if (inTry > 0) {
                    return inTry;
                } else {
                    System.out.println("Please enter a positive number");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a positive number");
            }
        }
    }

    // EFFECT: makes sure user input is a valid bank name
    public String checkBank() {
        String in;
        List<String> check = new ArrayList<>();
        for (Account a : bank.getBank()) {
            check.add(a.getBank());
        }
        while (true) {
            in = stringInput();
            if (check.contains(in)) {
                return in;
            } else {
                System.out.println("Please enter a valid account name");
                printAccountNames();
            }
        }
    }

    // EFFECT: makes sure user input is a valid bank account type
    public String checkAccount() {
        String in;
        while (true) {
            in = stringInput();
            if (in.equals("Chequeing") || in.equals("Savings") || in.equals("Credit")) {
                return in;
            } else {
                System.out.println("Please choose from \nChequeing, Savings, Credit");
            }
        }

    }

    // EFFECT: makes sure user input is a valid expense category
    public String checkExpense() {
        String in;
        List<String> check = new ArrayList<>();
        for (Expense e : category.getExpense()) {
            check.add(e.getExpense());
        }
        while (true) {
            in = stringInput();
            if (check.contains(in)) {
                return in;
            } else {
                System.out.println("Please enter a valid expense category");
                printExpenseCategories();
            }
        }
    }
}
