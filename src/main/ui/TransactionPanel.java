package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Account;
import model.Expense;
import model.Transaction;

// Represents a transaction menu
public class TransactionPanel extends PanelManager implements ActionListener {

    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel editPanel;
    private JPanel showAllPanel;
    private JPanel showExpPanel;
    private JPanel showInDatePanel;
    private JPanel monthPanel;
    private JPanel yearPanel;
    private JPanel bankPanel;
    private JPanel accPanel;
    private JPanel expPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton showAllButton;
    private JButton showInDateButton;
    private JButton showExpButton;
    JLabel label;
    private Object[] transactionHolder = {0, 0, 0, 0.0, "", "", ""};
    private String[] transactionLabels;
    private int month;
    private int year;
    private int line;
    private double amount;
    private String expense;
    private String accountName;
    private String accountType;

    // EFFECTS: intializes variables
    public TransactionPanel(MenuUI ui) {
        super(ui);
        buttonInit();
        panelInit();
        subPanelInit();
        initOther();
        expPanelInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    public void buttonInit() {
        addButton = new JButton("Add a transaction");
        removeButton = new JButton("<html><center>Remove<br>a transaction</center></html>");
        editButton = new JButton("<html><center>Edit a<br>previous transaction</center><html>");
        showAllButton = new JButton("<html><center>Show all<br>transactions</center><html>");
        showInDateButton = new JButton("<html><center>Show transactions<br>within a month/year</center><html>");
        showExpButton = new JButton("<html><center>Show all spendings<br>in an expense category</center><html>");
        backButton = new JButton("Return to main menu");
        buttonList = new JButton[] {
            addButton, removeButton, editButton, showAllButton, showInDateButton,
            showExpButton, backButton
        };
        addAction(this, buttonList);
    }

    // MODIFIES: this
    // EFFECTS: initializes the main panel
    public void panelInit() {
        label = new JLabel("<html><center>TRANSACTION<br>MENU</center><html>", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 4));
        mainPanel.add(label);
        for (JButton j : buttonList) {
            mainPanel.add(j);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes sub-panels
    public void subPanelInit() {
        createAddPanel();
        createEditPanel();
        createRemovePanel();
        createShowAllPanel();
        createShowInDatePanel();
        createExpPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes bank and acc panel
    public void bankAccPanelInit() {
        bankPanel = createBankPanel();
        bankPanelButtonInit();
        accPanel = createAccountPanel();
        accPanelButtonInit();
    }

    // MODIFIES: this
    // EFFECTSL initializes expense panel
    public void expPanelInit() {
        expPanel = new JPanel();
        expPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addExpPanelButtons(expPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes bank buttons
    public void bankPanelButtonInit() {
        for (int i = 0; i < bankPanel.getComponentCount(); i++) {
            JButton button = (JButton) bankPanel.getComponent(i);
            button.addActionListener(bankAction(button));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes account buttons
    public void accPanelButtonInit() {
        for (int i = 0; i < accPanel.getComponentCount(); i++) {
            JButton button = (JButton) accPanel.getComponent(i);
            button.addActionListener(accountAction(button));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes all other things
    public void initOther() {
        transactionLabels = addTransactionLabels();
    }

    // MODIFIES: this
    // EFFECTS: creates a list of strings prompting user to enter their transaction info
    public String[] addTransactionLabels() {
        return new String[] {
            "What is the number month of your transaction?",
            "What is the date of your transaction?",
            "What was the year of your transaction?",
            "What is the amount spent?",
            "What is the name of the store/vendor?",
            "What is the expense category?",
            "Any notes? (press enter to skip)",
            "What is the account name?",
            "What is the account type? Chequeing, Savings, Credit"
        };
    }






    // MODIFIES: this
    // EFFECTS: creates the panel to add a transaction
    public void createAddPanel() {
        addPanel = createInputPanel();
        JButton submit = (JButton) addPanel.getComponent(5);
        submit.addActionListener(addTransactionAction());
        JButton button = new JButton("Return to transaction menu");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(createBackButton(mainPanel));
        addPanel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel to edit a previous transaction
    public void createEditPanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel));
        editPanel = createShowPanel(button);
    }

    // MODIFIES: this
    // EFFECTS: removes a certain transaction by line number
    public void createRemovePanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel));
        removePanel = createShowPanel(button);
    }

    // MODIFIES: this
    // EFFECTS: creates show all transactions panel
    public void createShowAllPanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel));
        showAllPanel = createShowPanel(button);
    }

    // MODIFIES: this
    // EFFECTS: creates panel to show all transactions under a certain expense
    public void createExpPanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel));
        showExpPanel = createShowPanel(button);
        JLabel label = (JLabel) showExpPanel.getComponent(0);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        label.setText("Please select an expense");
        JPanel bottomPanel = (JPanel) showExpPanel.getComponent(2);
        bottomPanel.remove(0);
        bottomPanel.setLayout(new GridBagLayout());
        addExpButtons(bottomPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        bottomPanel.add(button, gbc);
    }

    // MODIFIES: this
    // EFFECTS: creates panel to show all transactions within a certain month/year
    public void createShowInDatePanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel)); 
        showInDatePanel = createShowPanel(button);
        monthYearPanelInit();
        JButton submit = (JButton) monthPanel.getComponent(5);
        JTextField text = (JTextField) monthPanel.getComponent(3);
        submit.addActionListener(monthAction(text, monthPanel));
        submit = (JButton) yearPanel.getComponent(5);
        text = (JTextField) yearPanel.getComponent(3);
        submit.addActionListener(yearAction(text, yearPanel));
    }

    // MODIFIES: this
    // EFFECTS: initializes the month and year input panel
    public void monthYearPanelInit() {
        monthPanel = createInputPanel();
        JButton button = new JButton("Return to transaction menu");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(createBackButton(mainPanel));
        monthPanel.add(button);
        yearPanel = createInputPanel();
        button = new JButton("Return to transaction menu");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(createBackButton(mainPanel));
        yearPanel.add(button);
    }






    // MODIFIES: this
    // EFFECTS: runs the remove panel and checks if there are any present transactions
    public void runRemovePanel() {
        updateScrollPane("Enter the line number you wish to remove", ui.getTransactionList(), removePanel);
        JPanel bottomPanel = (JPanel) removePanel.getComponent(2);
        bottomPanel.removeAll();
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel));
        if (ui.getTransactionList().size() != 0) {
            bottomPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 10, 10, 10);
            bottomPanel.add(button, gbc);
            gbc.gridx = 1;
            bottomPanel.add(textField, gbc);
            JButton submit = new JButton("Submit");
            submit.addActionListener(lineRemoveAction(textField, bottomPanel));
            gbc.gridx = 2;
            bottomPanel.add(submit, gbc);
            updateWithInput(textField, bottomPanel);
        } else {
            bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            bottomPanel.add(button);
        }
        refresh(removePanel);
    }

    // MODIFIES: MenuUI
    // EFFECTS: runs remove panel
    public void runRemoveTransaction() {
        ui.removeTransaction(line);
    }

    // MODIFIES: this
    // EFFECTS: runs edit panel and checks if there are any present transactions
    public void runEditPanel() {
        updateScrollPane("Enter the line number you wish to edit", ui.getTransactionList(), editPanel);
        JPanel bottomPanel = (JPanel) editPanel.getComponent(2);
        bottomPanel.removeAll();
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel));
        if (ui.getTransactionList().size() != 0) {
            bottomPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 10, 10, 10);
            bottomPanel.add(button, gbc);
            gbc.gridx = 1;
            bottomPanel.add(textField, gbc);
            JButton submit = new JButton("Submit");
            submit.addActionListener(lineEditAction(textField, bottomPanel));
            gbc.gridx = 2;
            bottomPanel.add(submit, gbc);
        } else {
            bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            bottomPanel.add(button);
        }
        updateWithInput(textField, bottomPanel);
    }
    
    // MODIFIES: MenuUI, this
    // EFFECTS: asks user for amount they wish to change to
    public void runEditAmount() {
        JPanel panel = createInputPanel();
        JButton submit = (JButton) panel.getComponent(5);
        JTextField text = (JTextField) panel.getComponent(3);
        submit.addActionListener(editAction(text, panel));
        JLabel label = (JLabel) panel.getComponent(1);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        label.setText("Enter the amount you wish to update it to");
        JButton button = new JButton("Return to transaction menu");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(createBackButton(mainPanel));
        panel.add(button);
        updateWithInput(text, panel);
        ui.switchPanel(panel);
    }

    // MODIFIES: MenuUI, this
    // EFFECTS: edits a previous transaction
    public void runEditTransaction() {
        ui.accountRefund(line);
        Account acc = ui.getAccount();
        Transaction tran = ui.getTransaction();
        ui.updateBank(acc.getBank(), tran.getAccountType(), -amount);
        ui.updateExpense();
        Expense expense = ui.getExpense();
        expense.updateSpending(amount);
        tran.updateAmount(amount);
        updateScreen(mainPanel, acc);
    }

    // MODIFIES: this
    // EFFECTS: runs add transaction panel
    public void runAddPanel() {
        bankAccPanelInit();
        createAddPanel();
        JLabel label = (JLabel) addPanel.getComponent(1);
        JTextField text = (JTextField) addPanel.getComponent(3);
        ind = 0;
        label.setText(transactionLabels[0]);
        updateWithInput(text, addPanel);
    }

        // MODIFIES: this
    // EFFECTS: runs the show all transactions panel
    public void runShowAllPanel() {
        String s = "Showing all transactions";
        List<Transaction> tracker = ui.getTransactionList();
        updateScrollPane(s, tracker, showAllPanel);
    }

    // EFFECTS: runs empty show expense transactiona panel
    public void runShowEmptyExpPanel() {
        List<Transaction> tracker = new ArrayList<>();
        updateScrollPane("Please select an expense", tracker, showExpPanel);
    }

    // EFFECTS: runs show expense transaction panel
    public void runShowExpPanel() {
        String s = "Showing all transactions under " + expense;
        List<Transaction> tracker = ui.getTracker().sortExpense(expense);
        updateScrollPane(s, tracker, showExpPanel);
    }

    // EFFECTS: runs panel to show all transactions within a certain month/year
    public void runShowInDatePanel() {
        String s = "Showing all transactions in " + month + "/" + year;
        List<Transaction> tracker = ui.getTracker().sortMonth(month, year);
        updateScrollPane(s, tracker, showInDatePanel);
    }

    // EFFECTS: gets month input
    public void runMonthInputPanel() {
        JLabel label = (JLabel) monthPanel.getComponent(1);
        JTextField text = (JTextField) monthPanel.getComponent(3);
        label.setText("Enter which month you would like to view");
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
    }
    
    // EFFECTS: gets year input
    public void runYearInputPanel() {
        JLabel label = (JLabel) yearPanel.getComponent(1);
        JTextField text = (JTextField) yearPanel.getComponent(3);
        label.setText("Enter which year you would like to view");
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
    }

    // MODIFIES: this
    // EFFECTS: runs bank panel for add transction
    public void runBankPanel() {
        JTextField text = (JTextField) addPanel.getComponent(3);
        JButton submit = (JButton) addPanel.getComponent(5);
        addPanel.remove(text);
        addPanel.remove(submit);
        addPanel.add(bankPanel, 3);
        refresh(addPanel);
    }

    // MODIFIES: this
    // EFFECTS: runs acc panel for add transction
    public void runAccPanel() {
        JPanel middlePanel = (JPanel) addPanel.getComponent(3);
        addPanel.remove(middlePanel);
        addPanel.add(accPanel, 3);
        refresh(addPanel);
    }

    // MODIFIES: this
    // EFFECTS: runs expense panel for add transction
    public void runExpPanel() {
        JTextField text = (JTextField) addPanel.getComponent(3);
        JButton submit = (JButton) addPanel.getComponent(5);
        addPanel.remove(text);
        addPanel.remove(submit);
        addPanel.add(expPanel, 3);
        refresh(addPanel);
    }





    // MODIFIES: MenuUI
    // EFFECTS: changes the values in MenuUI to update
    public void changeUITransaction() {
        ui.setMonth((int) transactionHolder[0]);
        ui.setDate((int) transactionHolder[1]);
        ui.setYear((int) transactionHolder[2]);
        ui.setAmount((double) transactionHolder[3]);
        ui.setStore((String) transactionHolder[4]);
        ui.setExp(expense);
        ui.setNote((String) transactionHolder[5]);
        ui.setAccountName(accountName);
        ui.setAccountType(accountType);
        ui.addTransaction();
    }

    // MODIFIES: middlePanel
    // EFFECTS: adds expense buttons to middle panel
    public void addExpPanelButtons(JPanel panel) {
        int[][] temp = {
            {0, 1, 2, 3, 4, 5, 6, 0, 1, 2, 3, 4, 5, 6}, 
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1}
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        List<Expense> categories = ui.getExpenses();
        JButton button;
        for (int i = 0; i < categories.size(); i++) {
            Expense exp = categories.get(i);
            button = new JButton(exp.getExpense());
            gbc.gridx = temp[0][i];
            gbc.gridy = temp[1][i];
            button.addActionListener(new ActionListener() {
                // EFFECTS: overrides actionPerformed
                @Override
                public void actionPerformed(ActionEvent e) {
                    expense = exp.getExpense();
                    ind++;
                    resetToTextAddPanel();
                }
            });
            panel.add(button, gbc);
        }
    }

    // MODIFIES: middlePanel
    // EFFECTS: adds expense buttons to middle panel
    public void addExpButtons(JPanel panel) {
        int[][] temp = {
            {0, 1, 2, 3, 4, 5, 6, 0, 1, 2, 3, 4, 5, 6}, 
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1}
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        List<Expense> categories = ui.getExpenses();
        JButton button;
        for (int i = 0; i < categories.size(); i++) {
            Expense exp = categories.get(i);
            button = new JButton(exp.getExpense());
            gbc.gridx = temp[0][i];
            gbc.gridy = temp[1][i];
            button.addActionListener(new ActionListener() {
                // EFFECTS: overrides actionPerformed
                @Override
                public void actionPerformed(ActionEvent e) {
                    expense = exp.getExpense();
                    runShowExpPanel();
                }
            });
            panel.add(button, gbc);
        }
    }

    // EFFECTS: returns a string of all transactions in tracker
    public String showTransaction(List<Transaction> tracker) {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < tracker.size(); i++) {
            Transaction t = tracker.get(i);
            content.append(i + 1).append(". ").append(t.printTransaction()).append("\n");
        }
        return content.toString();
    }

    // EFFECTS: adds list of transaction to a scroll pane
    public void updateScrollPane(String s, List<Transaction> tracker, JPanel panel) {
        JLabel label = (JLabel) panel.getComponent(0);
        JPanel middlePanel = (JPanel) panel.getComponent(1);
        JScrollPane scrollPane = (JScrollPane) middlePanel.getComponent(0);
        JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        if (tracker.size() == 0) {
            label.setText("There are currently no transactions");
        } else {
            label.setText(s);
        }
        String allTransactions = showTransaction(tracker);
        textArea.setText(allTransactions);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        refresh(panel);
    }

    // MODIFIES: this
    // EFFECTS: resets to text input in add panel
    public void resetToTextAddPanel() {
        JLabel label = (JLabel) addPanel.getComponent(1);
        label.setText(transactionLabels[ind]);
        JTextField text = new JTextField(40);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setMaximumSize(new Dimension(200, 40));
        JButton submit = new JButton("Submit");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.addActionListener(addTransactionAction());
        JPanel middlePanel = (JPanel) addPanel.getComponent(3);
        addPanel.remove(middlePanel);
        addPanel.add(text, 3);
        addPanel.add(submit, 5);
        updateWithInput(text, addPanel);
    }







    // EFFECTS: creates an ActionListener to check bank name input
    public ActionListener bankAction(JButton button) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                accountName = button.getText();
                JLabel label = (JLabel) addPanel.getComponent(1);
                ind++;
                label.setText(transactionLabels[ind]);
                runAccPanel();
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener to check account name input
    public ActionListener accountAction(JButton button) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                accountType = button.getText();
                changeUITransaction();
                updateScreen(mainPanel, ui.getAccount());
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for taking in an amount to change a transaction by
    public ActionListener editAction(JTextField text, JPanel panel) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                amount = doubleText(text.getText(), panel);
                if (ind != 0) {
                    runEditTransaction();
                }
                text.setText("");
                updateWithInput(text, panel);
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for taking in a line input for remove transaction
    public ActionListener lineRemoveAction(JTextField text, JPanel panel) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                line = intTextLast(text.getText(), panel) - 1;
                if (ind != 0 && 0 <= line) {
                    if (line < ui.getTransactionList().size()) {
                        runRemoveTransaction();
                        updateScreen(mainPanel, ui.getAccount());
                    } else {
                        panel.add(new JLabel("Please enter a valid line number"));
                    }
                }
                text.setText("");
                updateWithInput(text, removePanel);
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for taking in a line input in edit transaction
    public ActionListener lineEditAction(JTextField text, JPanel panel) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                line = intTextLast(text.getText(), panel) - 1;
                if (ind != 0 && 0 <= line) {
                    if (line < ui.getTransactionList().size()) {
                        runEditAmount();
                    } else {
                        panel.add(new JLabel("Please enter a valid line number"));
                    }
                }
                text.setText("");
                updateWithInput(text, editPanel);
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for year input
    public ActionListener yearAction(JTextField text, JPanel panel) {   
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                year = intText(text.getText(), panel);
                text.setText("");
                if (ind != 0) {
                    ui.switchPanel(showInDatePanel);
                    runShowInDatePanel();
                }
                updateWithInput(text, yearPanel);
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for month input
    public ActionListener monthAction(JTextField text, JPanel panel) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                month = intText(text.getText(), panel);
                text.setText("");
                if (ind != 0) {
                    ui.switchPanel(yearPanel);
                    runYearInputPanel();
                }
                updateWithInput(text, monthPanel);
            }
        };
        return al;
    }

    // MODIFIES: this
    // EFFECTS: creates a ActionListener for add transaction panel
    public ActionListener addTransactionAction() {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField text = (JTextField) addPanel.getComponent(3);
                JLabel label = (JLabel) addPanel.getComponent(1);
                addTransactionActionHelper();
                text.setText("");
                label.setText(transactionLabels[ind]);  
                updateWithInput(text, addPanel);
                if (ind == 5) {
                    runExpPanel();
                }
                if (ind == 7) {
                    runBankPanel();
                }
            }
        };
        return al;
    }

    // EFFECTS: decides which panel to go to next
    private void addTransactionActionHelper() {
        JTextField text = (JTextField) addPanel.getComponent(3);
        if (0 <= ind && ind <= 2) {
            transactionHolder[ind] = intText(text.getText(), addPanel);
        } else if (ind == 3) {
            transactionHolder[ind] = doubleText(text.getText(), addPanel);
        } else if (ind == 4 || ind == 6) {
            transactionHolder[ind] = stringText(text.getText(), addPanel);
        }
    }

    // EFFECTS: communication with MenuUI
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            ui.backClick();
        } else if (e.getSource() == addButton) {
            ui.addTransactionClick();
        } else if (e.getSource() == removeButton) {
            ui.removeClick();
        } else if (e.getSource() == editButton) {
            ui.editClick();
        } else if (e.getSource() == showAllButton) {
            ui.showAllClick();
        } else if (e.getSource() == showInDateButton) {
            ui.showInDateClick();
        } else if (e.getSource() == showExpButton) {
            ui.showExpTranClick();
        }
    }





    // GETTER
    public JPanel getAddPanel() {
        return addPanel;
    }

    // GETTER
    public JPanel getShowAllPanel() {
        return showAllPanel;
    }

    // GETTER
    public JPanel getShowExpPanel() {
        return showExpPanel;
    }

    // GETTER
    public JPanel getMonthPanel() {
        return monthPanel;
    }

    // GETTER
    public JPanel getRemovePanel() {
        return removePanel;
    }

    // GETTER
    public JPanel getEditPanel() {
        return editPanel;
    }
}
