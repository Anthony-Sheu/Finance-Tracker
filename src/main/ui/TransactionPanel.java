package ui;

import java.awt.BorderLayout;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Expense;
import model.Tracker;
import model.Transaction;

// Represents a transaction menu
public class TransactionPanel extends PanelManager implements ActionListener {

    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel editPanel;
    private JPanel showAllPanel;
    private JPanel showExpPanel;
    private JPanel showInDatePanel;
    private JPanel refundPanel;
    private JPanel monthPanel;
    private JPanel yearPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton showAllButton;
    private JButton showInDateButton;
    private JButton showExpButton;
    private JButton refundButton;
    JLabel label;
    private Object[] transactionHolder = {0, 0, 0, 0.0, "", "", "", "", ""};
    private String[] transactionLabels;
    private int month;
    private int year;
    private int line;
    private String expense;

    // EFFECTS: intializes variables
    public TransactionPanel(MenuUI ui) {
        super(ui);
        buttonInit();
        panelInit();
        subPanelInit();
        initOther();
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
        refundButton = new JButton("Refund a transaction");
        backButton = new JButton("Return to main menu");
        buttonList = new JButton[] {
            addButton, removeButton, editButton, showAllButton, showInDateButton,
            showExpButton, refundButton, backButton
        };
        addAction(this, buttonList);
    }

    // MODIFIES: this
    // EFFECTS: initializes the main panel
    public void panelInit() {
        label = new JLabel("TRANSACTION MENU", JLabel.CENTER);
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
        refundPanel = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes all other things
    public void initOther() {
        transactionLabels = addTransactionLabels();
    }

    // MODIFIES: this
    // EFFECTS: creates the panel to add a transaction
    public void createAddPanel() {
        addPanel = createInputPanel();
        JButton submit = (JButton) addPanel.getComponent(5);
        submit.addActionListener(addTransactionAction());
    }

    // MODIFIES: this
    // EFFECTS: creates the panel to edit a previous transaction
    public void createEditPanel() {
        
    }

    // MODIFIES: this
    // EFFECTS: removes a certain transaction by line number
    public void createRemovePanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel, button));
        removePanel = createShowPanel(button);
    }

    // MODIFIES: this
    // EFFECTS: 
    public void runRemovePanel() {
        String s = "Showing all transactions, enter the line number you wish to remove";
        List<Transaction> tracker = ui.getTransaction();
        updateScrollPane(s, tracker, removePanel);
        JPanel bottomPanel = (JPanel) removePanel.getComponent(2);
        bottomPanel.removeAll();
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel, button));
        if (ui.getTransaction().size() != 0) {
            bottomPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;
            bottomPanel.add(button, gbc);
            gbc.gridx = 1;
            bottomPanel.add(textField, gbc);
            JButton submit = new JButton("Submit");
            submit.addActionListener(lineAction(textField, bottomPanel));
            gbc.gridx = 2;
            bottomPanel.add(submit, gbc);
        } else {
            bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            bottomPanel.add(button);
        }
    }

    // MODIFIES: MenuUI
    // EFFECTS: runs remove panel
    public void runRemoveTransaction() {
        ui.removeTransaction(line);
    }

    // MODIFIES: this
    // EFFECTS: runs add transaction panel
    public void runAddPanel() {
        JLabel label = (JLabel) addPanel.getComponent(1);
        JTextField text = (JTextField) addPanel.getComponent(3);
        ind = 0;
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
        label.setText(transactionLabels[0]);
        refresh(addPanel);
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

    // MODIFIES: MenuUI
    // EFFECTS: changes the values in MenuUI to update
    public void changeUITransaction(Object[] temp) {
        ui.setMonth((int) temp[0]);
        ui.setDate((int) temp[1]);
        ui.setYear((int) temp[2]);
        ui.setAmount((double) temp[3]);
        ui.setStore((String) temp[4]);
        ui.setExpense((String) temp[5]);
        ui.setNote((String) temp[6]);
        ui.setAccountName((String) temp[7]);
        ui.setAccountType((String) temp[8]);
        ui.addTransaction();
    }

    // MODIFIES: this
    // EFFECTS: creates show all transactions panel
    public void createShowAllPanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel, button));
        showAllPanel = createShowPanel(button);
    }

    // MODIFIES: this
    // EFFECTS: runs the show all transactions panel
    public void runShowAllPanel() {
        String s = "Showing all transactions";
        List<Transaction> tracker = ui.getTransaction();
        updateScrollPane(s, tracker, showAllPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates panel to show all transactions under a certain expense
    public void createExpPanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel, button));
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
                @Override
                public void actionPerformed(ActionEvent e) {
                    expense = exp.getExpense();
                    runShowExpPanel();
                }
            });
            panel.add(button, gbc);
        }
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

    // MODIFIES: this
    // EFFECTS: creates panel to show all transactions within a certain month/year
    public void createShowInDatePanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel, button)); 
        showInDatePanel = createShowPanel(button);
        monthPanel = createInputPanel();
        JButton submit = (JButton) monthPanel.getComponent(5);
        JTextField text = (JTextField) monthPanel.getComponent(3);
        submit.addActionListener(monthAction(text, monthPanel));
        yearPanel = createInputPanel();
        submit = (JButton) yearPanel.getComponent(5);
        text = (JTextField) yearPanel.getComponent(3);
        submit.addActionListener(yearAction(text, yearPanel));
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

    // EFFECTS: returns a string of all transactions in tracker
    public String showTransaction(List<Transaction> tracker) {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < tracker.size(); i++) {
            Transaction t = tracker.get(i);
            content.append(i+1).append(". ").append(t.printTransaction()).append("\n");
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

    // EFFECTS: creates an ActionListener for taking in a line input
    public ActionListener lineAction(JTextField text, JPanel panel) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                line = intText(text.getText(), panel) - 1;
                if (ind != 0 && 0 <= line) {
                    if (line < ui.getTransaction().size()) {
                        runRemoveTransaction();
                        ui.transactionClick();
                    } else {
                        panel.add(new JLabel("Please enter a valid line number"));
                    }
                }
                text.setText("");
                refresh(removePanel);
                SwingUtilities.invokeLater(() -> {
                    text.requestFocusInWindow();
                });
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for year input
    public ActionListener yearAction(JTextField text, JPanel panel) {   
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                year = intText(text.getText(), panel);
                text.setText("");
                if (ind != 0) {
                    ui.switchPanel(showInDatePanel);
                    runShowInDatePanel();
                }
                refresh(yearPanel);
                SwingUtilities.invokeLater(() -> {
                    text.requestFocusInWindow();
                });
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for month input
    public ActionListener monthAction(JTextField text, JPanel panel) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                month = intText(text.getText(), panel);
                text.setText("");
                if (ind != 0) {
                    ui.switchPanel(yearPanel);
                    runYearInputPanel();
                }
                refresh(monthPanel);
                SwingUtilities.invokeLater(() -> {
                    text.requestFocusInWindow();
                });
            }
        };
        return al;
    }

    // MODIFIES: this
    // EFFECTS: creates a ActionListener for add transaction panel
    public ActionListener addTransactionAction() {
        JTextField text = (JTextField) addPanel.getComponent(3);
        JLabel label = (JLabel) addPanel.getComponent(1);
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (0 <= ind && ind <= 2) {
                    transactionHolder[ind] = intText(text.getText(), addPanel);
                } else if (ind == 3) {
                    transactionHolder[ind] = doubleText(text.getText(), addPanel);
                } else if (4 <= ind && ind <= 8) {
                    transactionHolder[ind] = stringText(text.getText(), addPanel);
                }
                text.setText("");
                if (ind < 9) {
                    label.setText(transactionLabels[ind]);  
                } else {
                    changeUITransaction(transactionHolder);
                    ui.backClick();
                }
                refresh(addPanel);
                SwingUtilities.invokeLater(() -> {
                    text.requestFocusInWindow();
                });
            }
        };
        return al;
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
}
