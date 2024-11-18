package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import model.Account;
import model.Banks;
import model.Expense;
import model.Tracker;
import model.Transaction;

// represents user interface for main menu
public class MenuUI extends Menu {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JFrame frame;
    private TransactionPanel transactionPanel;
    private BankPanel bankPanel;
    private MenuPanel menuPanel;
    private TransferPanel transferPanel;
    private CreditPanel creditPanel;
    private PersistencePanel persistencePanel;
    private int month;
    private int date;
    private int year;
    private double amount;
    private String store;
    private String exp;
    private String note;
    private String accountName;
    private String accountType;
    private double cheq;
    private double save;
    private double cred;
    private String bankName;
    private double credLim;
    private boolean bankLoad;
    private boolean tranLoad;
    private boolean bankSave;
    private boolean tranSave;
    private boolean loadError;
    private boolean saveError;

    // EFFECTS: constructs main frame and initializes every thing else
    public MenuUI() {     
        super();
        super.init();
        panelInit();
        frameInit();
        varInit();
        startup();
    }

    // MODIFIES: this
    // EFFECTS: initializes holder fields
    public void varInit() {
        month = 0;
        date = 0;
        year = 0;
        amount = 0.0;
        store = "";
        exp = "";
        note = "";
        accountName = "";
        accountType = "";
        cheq = 0.0;
        save = 0.0;
        cred = 0.0;
        bankName = "";
        credLim = 0.0;
        loadError = false;
        saveError = false;
    }

    // MODIFIES: this
    // EFFECTS: checks if there is a save file, if there is isn't, prompts user to enter
    // their banking information, if there is, then asks user if they want to load it
    public void startup() {
        if(!bankReader.checkFile()) {
            addBankClick();
        } else {
            loadClick();
        }
    }

    // MODIFIES: this
    // EFFECTS: checks for user load choices
    public void load() {
        if (bankLoad) {
            loadBankFile();
        }
    }

    // MODIFIES: this
    // EFFECTS: checks for user save choices
    public void save() {
        if (bankSave) {
            saveBankFile();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads bank information from saved json file
    public void loadBankFile() {
        try {
            super.loadBankFile();
            if (trackerReader.checkFile() && categoryReader.checkFile() && tranLoad) {
                checkTrackerFile();
            }
        } catch (IOException e) {
            loadError = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if there is information in tracker file, and if there is, prompts
    // user if they would like to load it
    public void checkTrackerFile() {
        try {
            super.checkTrackerFile();
        } catch (IOException e) {
            loadError = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: saves banking information based on bankSave
    public void saveBankFile() {
        try {
            super.saveBankFile();
            if (tranSave) {
                saveTrackerFile();
            }
        } catch (FileNotFoundException e) {
            loadError = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes frame
    public void frameInit() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Finance Tracker GUI");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the sub-panels
    public void panelInit() {
        menuPanel = new MenuPanel(this);
        transactionPanel = new TransactionPanel(this);
        bankPanel = new BankPanel(this);
        transferPanel = new TransferPanel(this);
        creditPanel = new CreditPanel(this);
        persistencePanel = new PersistencePanel(this);
    }

    // MODIFIES: this
    // EFFECTS: adds new transaction
    public void addTransaction() {
        super.addTransaction(month, date, year, amount, store, exp, note, accountName, accountType);
        super.updateBank(accountName, accountType, -amount);
    }

    // MODIFIES: this
    // EFFECTS: adds new bank account
    public void addBank() {
        super.addBank(bankName, cheq, save, cred, credLim);
    }

    // EFFECTS: switches to prompt load panel
    public void loadClick() {
        persistencePanel.runBankLoad();
        switchPanel(persistencePanel.getMainPanel());
    }

    // EFFECTS: switches to transaction menu
    public void transactionClick() {
        switchPanel(transactionPanel.getMainPanel());
    }

    // MODIFIES: this
    // EFFECTS: add new transaction
    public void addTransactionClick() {
        transactionPanel.runAddPanel();
        switchPanel(transactionPanel.getAddPanel());
    }

    // MODIFIES: this
    // EFFECTS: removes transaction
    public void removeClick() {
        transactionPanel.runRemovePanel();
        switchPanel(transactionPanel.getRemovePanel());
    }

    // EFFECTS: edits transaction
    public void editClick() {
        transactionPanel.runEditPanel();
        switchPanel(transactionPanel.getEditPanel());
    }

    // EFFECTS: shows all transactions
    public void showAllClick() {
        transactionPanel.runShowAllPanel();
        switchPanel(transactionPanel.getShowAllPanel());
    }

    // EFFECTS: shows all transactions in certain month/year
    public void showInDateClick() {
        transactionPanel.runMonthInputPanel();
        switchPanel(transactionPanel.getMonthPanel());
    }

    // EFFECTS: shows a transactions in a certain expense category
    public void showExpTranClick() {
        transactionPanel.runShowEmptyExpPanel();
        switchPanel(transactionPanel.getShowExpPanel());
    }

    // EFFECTS: opens bank menu
    public void bankClick() {
        switchPanel(bankPanel.getMainPanel());
    }

    // MODIFIES: this
    // EFFECTS: adds bank
    public void addBankClick() {
        bankPanel.runAddBankPanel();
        switchPanel(bankPanel.getAddBankPanel());
    }

    // EFFECTS: checks bank account balances
    public void bankBalanceClick() {
        bankPanel.runBalancePanel();
        switchPanel(bankPanel.getBalancePanel());
    }

    // EFFECTS: transfer money between two amounts
    public void transferClick() {
        transferPanel.runTransferPanel();
        switchPanel(transferPanel.getMainPanel());
    }

    // EFFECTS: goes to credit card menu
    public void creditClick() {
        switchPanel(creditPanel.getMainPanel());
    }

    // EFFECTS: pays credit card from specified account
    public void payClick() {
        creditPanel.runPayPanel();
        switchPanel(creditPanel.getPayPanel());
    }

    // EFFECTS: changes credit card limit
    public void limitClick() {
        creditPanel.runLimitPanel();
        switchPanel(creditPanel.getLimitPanel());
    }

    // EFFECTS: closes the program
    public void quitClick() {
        persistencePanel.runBankSave();
        switchPanel(persistencePanel.getMainPanel());
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
    public void setExp(String expense) {
        this.exp = expense;
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

    // SETTER
    public void setCheq(double cheq) {
        this.cheq = cheq;
    }

    // SETTER
    public void setSave(double save) {
        this.save = save;
    }

    // SETTER
    public void setCred(double cred) {
        this.cred = cred;
    }

    // SETTER
    public void setBankName(String name) {
        this.bankName = name;
    }

    // SETTER
    public void setCredLim(double lim) {
        this.credLim = lim;
    }

    // SETTER
    public void setBankLoad(Boolean bool) {
        bankLoad = bool;
    }

    // SETTER
    public void setTranLoad(Boolean bool) {
        tranLoad = bool;
    }

    // SETTER
    public void setBankSave(Boolean bool) {
        bankSave = bool;
    }

    // SETTER
    public void setTranSave(Boolean bool) {
        tranSave = bool;
    }

    // SETTER
    public void setLoadError(Boolean bool) {
        loadError = false;
    }





    // GETTER
    public List<Transaction> getTransactionList() {
        return tracker.getTracker();
    }

    // GETTER
    public Tracker getTracker() {
        return tracker;
    }

    // GETTER
    public List<Account> getBanks() {
        return bank.getBank();
    }

    // GETTER
    public List<Expense> getExpenses() {
        return category.getExpense();
    }

    // GETTER
    public Account getAccount() {
        return account;
    }

    // GETTER
    public Transaction getTransaction() {
        return transaction;
    }

    // GETTER
    public Expense getExpense() {
        return expense;
    }

    // GETTER
    public Banks getBank() {
        return bank;
    }

    // GETTER
    public JPanel getMenuPanel() {
        return menuPanel.getMainPanel();
    }

    // GETTER
    public boolean getLoadError() {
        return loadError;
    }

    // GETTER
    public boolean getSaveError() {
        return saveError;
    }
}
