package ui;

import java.util.*;

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.IntegerConversion;

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

    public Menu(){
        start();
    }

    public void start() {
        int option = 0;
        init();
        while (true) {
            option = mainMenu();
            if (option == 7) {
                break;
            } else {
                process(option);
            }
        }
        System.out.println("Goodbye!");
    }

    public void init() {
        bank = new Banks();
        category = new Categories();
        tracker = new Tracker();
        input = new Scanner(System.in);
        System.out.println("======================START UP======================");
        System.out.println("Please start by entering your banking information");
        addBank();
    }

    public int mainMenu() {
        System.out.println("======================MAIN MENU======================");
        System.out.println("Would you like to (enter the number):");
        System.out.println("1. Add a transaction");
        System.out.println("2. See all transactions");
        System.out.println("3. See all transactions in a given month");
        System.out.println("4. See all transactions by an expense category");
        System.out.println("5. Add new bank account");
        System.out.println("6. See bank account balances");
        System.out.println("7. Quit");
        String command = input.nextLine();
        return Integer.parseInt(command);
    }

    public void process(int num) {
        if(num == 1) {
            addTransaction();
        } else if (num == 2) {
            showTransaction(tracker.getTracker());
        } else if (num == 3) {
            showMonthTransaction();
        } else if (num == 4) {
            showExpenseTransaction();
        } else if (num == 5) {
            addBank();
        } else {
            showBank();
        }
    }

    public void enter() {
        System.out.println("Press enter to continue");
        input.nextLine();
    }

    public void addTransaction() {
        String command;
        int month;
        int date;
        int year;
        int amount;
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
        amount = -Integer.parseInt(split[3]);
        store = split[4];
        expense = split[5];
        note = split[6];
        account = split[7];
        transaction = new Transaction(month, date, year, amount, store, expense, note, account);
        tracker.addTransaction(transaction);
        updateBank(account, amount);
    }

    public void updateBank(String info, int amount) {
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

    public void showTransaction(List<Transaction> show) {
        System.out.println("=====================================================");
        for(int i = 0; i < show.size(); i++) {
            System.out.print(Integer.toString(i+1)+". ");
            System.out.println(show.get(i).printTransaction());
        }
    }

    public void showMonthTransaction() {
        System.out.println("=====================================================");
        System.out.println("What year would you like to view?");
        int year = Integer.parseInt(input.nextLine());
        System.out.println("What month (in numbers)?");
        int month = Integer.parseInt(input.nextLine());
        showTransaction(tracker.sortMonth(month, year));
        enter();
    }

    public void showExpenseTransaction() {
        System.out.println("=====================================================");
        System.out.println("What expense category would you like to view?");
        String expense = input.nextLine();
        showTransaction(tracker.sortExpense(expense));
        enter();
    }

    public void addBank() {
        System.out.println("=====================================================");
        System.out.println("What is the name of your banking institution?");
        String name = input.nextLine();
        System.out.println("Please enter your chequeing balance");
        int cheq = Integer.parseInt(input.nextLine());
        System.out.println("Please enter your savings balance");
        int save = Integer.parseInt(input.nextLine());
        System.out.println("Please enter your credit balance");
        int cred = Integer.parseInt(input.nextLine());
        System.out.println("Please enter your credit limit");
        int credLim = Integer.parseInt(input.nextLine());
        account = new Account(cheq, save, cred, name, credLim);
        bank.newAccount(account);
    }
    
    public void showBank() {
        System.out.println("=====================================================");
        System.out.println("Your banking information");
        for(Account acc : bank.getBank()) {
            System.out.println(acc.printAccount());
        }
        enter();
    }
    
}
