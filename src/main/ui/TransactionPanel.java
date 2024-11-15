package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Tracker;
import model.Transaction;

// Represents a transaction menu
public class TransactionPanel extends PanelManager implements ActionListener {

    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel editPanel;
    private JPanel showAllPanel;
    private JPanel showInDatePanel;
    private JPanel showExpPanel;
    private JPanel showAllExpPanel;
    private JPanel refundPanel;
    private JPanel monthPanel;
    private JPanel yearPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton showAllButton;
    private JButton showInDateButton;
    private JButton showExpButton;
    private JButton showAllExpButton;
    private JButton refundButton;
    JLabel label;
    private Object[] transactionHolder = {0, 0, 0, 0.0, "", "", "", "", ""};
    private String[] transactionLabels;
    private int month;
    private int year;

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
        showAllExpButton = new JButton("<html><center>Show spendings in<br>all expense categories</center><html>");
        refundButton = new JButton("Refund a transaction");
        backButton = new JButton("Return to main menu");
        buttonList = new JButton[] {
            addButton, removeButton, editButton, showAllButton, showInDateButton,
            showExpButton, showAllExpButton, refundButton, backButton
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
        removePanel = new JPanel();
        editPanel = new JPanel();
        createShowAllPanel();
        createShowInDatePanel();
        showExpPanel = new JPanel();
        showAllExpPanel = new JPanel();
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
    }

    // MODIFIES: this
    // EFFECTS: runs add transaction panel
    public void runAddPanel() {
        JButton submit = (JButton) addPanel.getComponent(5);
        JLabel label = (JLabel) addPanel.getComponent(1);
        JTextField text = (JTextField) addPanel.getComponent(3);
        ind = 0;
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
        label.setText(transactionLabels[0]);
        submit.addActionListener(addTransactionAction());
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
        JLabel label = (JLabel) showAllPanel.getComponent(0);
        JPanel middlePanel = (JPanel) showAllPanel.getComponent(1);
        JScrollPane scrollPane = (JScrollPane) middlePanel.getComponent(0);
        JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        if (ui.getTransaction().size() == 0) {
            label.setText("There are currently no transactions");
        } else {
            label.setText("Showing all transactions");
            String allTransactions = showTransaction(ui.getTransaction());
            textArea.setText(allTransactions);
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        }
        refresh(showAllPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates panel to show all transactions within a certain month/year
    public void createShowInDatePanel() {
        JButton button = new JButton("Return to transaction menu");
        button.addActionListener(createBackButton(mainPanel, button)); 
        showInDatePanel = createShowPanel(button);
        monthPanel = createInputPanel();
        yearPanel = createInputPanel();
    }

    // EFFECTS: runs panel to show all transactions within a certain month/year
    public void runShowInDatePanel() {
        JLabel label = (JLabel) showInDatePanel.getComponent(0);
        JPanel middlePanel = (JPanel) showInDatePanel.getComponent(1);
        JScrollPane scrollPane = (JScrollPane) middlePanel.getComponent(0);
        JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        Tracker tracker = ui.getTracker();
        tracker.sortMonth(month, year);
        if (ui.getTransaction().size() == 0) {
            label.setText("There are currently no transactions");
        } else {
            label.setText("Showing all transactions");
            String allTransactions = showTransaction(ui.getTransaction());
            textArea.setText(allTransactions);
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        }
        refresh(showAllPanel);
    }

    // EFFECTS: gets month input
    public void runMonthInputPanel() {
        JButton submit = (JButton) monthPanel.getComponent(5);
        JLabel label = (JLabel) monthPanel.getComponent(1);
        JTextField text = (JTextField) monthPanel.getComponent(3);
        label.setText("Enter which month you would like to view");
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
        submit.addActionListener(monthAction(submit, text, monthPanel));
    }

    // EFFECTS: gets year input
    public void runYearInputPanel() {
        JButton submit = (JButton) yearPanel.getComponent(5);
        JLabel label = (JLabel) yearPanel.getComponent(1);
        JTextField text = (JTextField) yearPanel.getComponent(3);
        label.setText("Enter which year you would like to view");
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
        submit.addActionListener(yearAction(submit, text, yearPanel));
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

    // EFFECTS: creates an ActionListener for year input
    public ActionListener yearAction(JButton button, JTextField text, JPanel panel) {
        ind = 0;
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    year = intText(text.getText(), panel);
                    if (ind != 0) {
                        ui.switchPanel(showInDatePanel);
                        runShowInDatePanel();
                    }
                    refresh(yearPanel);
                    SwingUtilities.invokeLater(() -> {
                        text.requestFocusInWindow();
                    });
                }
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for month input
    public ActionListener monthAction(JButton button, JTextField text, JPanel panel) {
        ind = 0;
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    month = intText(text.getText(), panel);
                    if (ind != 0) {
                        ui.switchPanel(yearPanel);
                        runYearInputPanel();
                    }
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
                if (ind < 9) {
                    label.setText(transactionLabels[ind]);
                    text.setText("");
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
            
        } else if (e.getSource() == editButton) {
            
        } else if (e.getSource() == showAllButton) {
            ui.showAllClick();
        } else if (e.getSource() == showInDateButton) {
            ui.showInDateClick();
        } else if (e.getSource() == showExpButton) {
            
        } else if (e.getSource() == showAllButton) {
            
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
    public JPanel getShowInDatePanel() {
        return showInDatePanel;
    }

    // GETTER
    public JPanel getMonthPanel() {
        return monthPanel;
    }
}
