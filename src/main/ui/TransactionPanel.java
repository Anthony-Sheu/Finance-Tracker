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

import model.Transaction;

// Represents a transaction menu
public class TransactionPanel extends PanelManager implements ActionListener {

    private JPanel mainPanel;
    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel editPanel;
    private JPanel showAllPanel;
    private JPanel showInDatePanel;
    private JPanel showExpPanel;
    private JPanel showAllExpPanel;
    private JPanel refundPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton showAllButton;
    private JButton backButton;
    private JButton backTranButton;
    private JButton showInDateButton;
    private JButton showExpButton;
    private JButton showAllExpButton;
    private JButton refundButton;
    JLabel label;
    private MenuUI ui;
    private Object[] transactionHolder = {0, 0, 0, 0.0, "", "", "", "", ""};
    private String[] transactionLabels;

    // EFFECTS: intializes variables
    public TransactionPanel(MenuUI ui) {
        buttonInit();
        panelInit();
        subPanelInit();
        initOther();
        this.ui = ui;
    }

    public void buttonInit() {
        addButton = new JButton("Add a transaction");
        removeButton = new JButton("<html><center>Remove<br>a transaction</center></html>");
        editButton = new JButton("<html><center>Edit a<br>previous transaction</center><html>");
        showAllButton = new JButton("<html><center>Show all<br>transactions</center><html>");
        showInDateButton = new JButton("<html><center>Show transactions<br>within a date/year</center><html>");
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

    public void panelInit() {
        label = new JLabel("TRANSACTION MENU", JLabel.CENTER);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 4));
        mainPanel.add(label);
        for (JButton j : buttonList) {
            mainPanel.add(j);
        }
        backTranButton = new JButton("Return to transaction menu");
        backTranButton.addActionListener(this);
        mainPanel.add(backTranButton);
    }

    public void subPanelInit() {
        createAddPanel();
        removePanel = new JPanel();
        editPanel = new JPanel();
        createShowAllPanel();
        showInDatePanel = new JPanel();
        showExpPanel = new JPanel();
        showAllExpPanel = new JPanel();
        refundPanel = new JPanel();
    }

    public void initOther() {
        transactionLabels = addTransactionLabels();
    }

    // MODIFIES: this
    // EFFECTS: creates the panel to add a transaction
    public void createAddPanel() {
        addPanel = createInputPanel();
    }

    public void runAddPanel() {
        JButton submit = (JButton) addPanel.getComponent(5);
        JLabel label = (JLabel) addPanel.getComponent(1);
        JTextField text = (JTextField) addPanel.getComponent(3);
        ind = 0;
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
        label.setText(transactionLabels[0]);
        refresh(addPanel);
        submit.addActionListener(addTransactionAction());
    }

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
    }

    public void createShowAllPanel() {
        showAllPanel = createShowPanel(backTranButton);
    }

    public void runShowAllPanel() {
        JLabel label = (JLabel) showAllPanel.getComponent(0);
        JPanel middlePanel = (JPanel) showAllPanel.getComponent(1);
        JScrollPane scrollPane = (JScrollPane) middlePanel.getComponent(0);
        JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
        label.setText("Showing All Transactions");
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        String allTransactions = showTransaction(ui.getTransaction());
        textArea.setText(allTransactions);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        refresh(showAllPanel);
    }

    public String showTransaction(List<Transaction> tracker) {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < tracker.size(); i++) {
            Transaction t = tracker.get(i);
            content.append(i+1).append(". ").append(t.printTransaction()).append("\n");
        }
        return content.toString();
    }

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
            
        } else if (e.getSource() == showExpButton) {
            
        } else if (e.getSource() == showAllButton) {
            
        } else if (e.getSource() == backTranButton) {
            ui.transactionClick();
        }
    }

    // GETTER
    public JPanel getAddPanel() {
        return addPanel;
    }
    
    // GETTER
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // GETTER
    public JPanel getShowAllPanel() {
        return showAllPanel;
    }
}
