package ui;

import java.util.*;

import javax.swing.*;

import model.Transaction;

// represents user interface for main menu
public class MenuUI extends Menu implements Communication {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JFrame frame;
    private TransactionPanel transactionPanel;
    private MenuPanel menuPanel;
    private int month;
    private int date;
    private int year;
    private double amount;
    private String store;
    private String expense;
    private String note;
    private String accountName;
    private String accountType;

    // EFFECTS: constructs main frame and initializes every thing else
    public MenuUI() {
        super();
        super.init();
        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes main frame and panel
    public void init() {
        frameInit();
        panelInit();
        frame.setContentPane(menuPanel.getMainPanel());
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes frame
    public void frameInit() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Finance Tracker GUI");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: initializes the sub-panels
    public void panelInit() {
        transactionPanel = new TransactionPanel(this);
        menuPanel = new MenuPanel(this);
    }

    // EFFECTS: switches to transaction menu
    public void transactionClick() {
        switchPanel(transactionPanel.getMainPanel());
    }

    // MODIFIES: this
    // EFFECTS: add new transaction
    public void addTransactionClick() {
        switchPanel(transactionPanel.getAddPanel());
        transactionPanel.runAddPanel();
        // super.addTransaction(month, date, year, amount, store, expense, note, accountName, accountType);
        // super.updateBank(accountName, accountName, -amount);
    }

    public void showAllClick() {
        switchPanel(transactionPanel.getShowAllPanel());
        transactionPanel.runShowAllPanel();
    }

    // EFFECTS: closes the program
    public void quitClick() {
        frame.dispose();
    }

    // EFFECTS: returns from sub-panels to main panel
    public void backClick() {
        switchPanel(menuPanel.getMainPanel());
    }

    // MODIFIES: this
    // EFFECTS: switches current panel
    public void switchPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    // SETTER
    public void setMonth(int month) {
        this.month = month;
    }

    // SETTER
    public void setDate(int date) {
        this.date = date;
    }

    // SETTER
    public void setYear(int year) {
        this.year = year;
    }

    // SETTER
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // SETTER
    public void setStore(String store) {
        this.store = store;
    }

    // SETTER
    public void setExpense(String expense) {
        this.expense = expense;
    }

    // SETTER
    public void setNote(String note) {
        this.note = note;
    }

    // SETTER 
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    // SETTER
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    // GETTER
    public List<Transaction> getTransaction() {
        return tracker.getTracker();
    }

}
