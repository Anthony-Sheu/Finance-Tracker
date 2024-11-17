package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import org.junit.internal.builders.JUnit3Builder;

import model.Account;


// Represents a panel for credit methods
public class CreditPanel extends PanelManager implements ActionListener {

    private GridBagConstraints gbc;
    private JLabel label;
    private JButton payButton;
    private JButton limitButton;
    private JPanel payPanel;
    private JPanel limitPanel;
    private JPanel bankPanel;
    private JPanel accPanel;
    private String[] payLabels;
    private String accountName1;
    private String accountName2;
    private String accountType;
    private double amount;

    // constructor
    public CreditPanel(MenuUI ui) {
        super(ui);
        buttonInit();
        labelInit();
        panelInit();
        subPanelInit();
        otherInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    public void buttonInit() {
        backButton = new JButton("Return to main menu");
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(200, 40));
        payButton = new JButton("Pay credit card");
        payButton.addActionListener(this);
        limitButton = new JButton("Change credit limit");
        limitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes labels
    public void labelInit() {
        label = new JLabel("CREDIT MENU", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
    }

    // MODIFIES: this
    // EFFECTS: initializes sub panels
    public void subPanelInit() {
        accPanel = createAccountPanel();
        accPanelButtonInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes main menu
    public void panelInit() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(label, gbc);
        panelAddButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes bank buttons on bank panel
    public void bankPanelButtonInit() {
        List<Account> bank = ui.getBanks();
        for (int i = 0; i < bank.size(); i++) {
            JButton button =  (JButton) bankPanel.getComponent(i);
            button.addActionListener(bankAction(button.getText()));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes account buttons on acc panel
    public void accPanelButtonInit() {
        JButton credit = (JButton) accPanel.getComponent(2);
        accPanel.remove(credit);
        for (int i = 0; i < 2; i++) {
            JButton button = (JButton) accPanel.getComponent(i);
            button.addActionListener(accountAction(button.getText()));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes aggregated labels
    public void otherInit() {
        payLabels = new String[] {
            "Which credit account would you like to pay?",
            "Which banking institution would you like to pay out of?",
            "Which account would you like to pay out of?",
            "How much would you like to pay?"
        };
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to panel
    public void panelAddButtons() {
        gbc.gridwidth = 1;
        gbc.insets = new Insets(25, 5, 25, 5);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 0.1;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(payButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(limitButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(backButton, gbc);
    }

    // MODIFIES: this
    // EFFECTS: creates pay panel
    public void createPayPanel() {
        payPanel = createInputPanel();
    }

    // EFFECTS: runs pay panel
    public void runPayPanel() {
        ind = 0;
        createPayPanel();
        bankPanel = createBankPanel();
        JLabel label = (JLabel) payPanel.getComponent(1);
        label.setText(payLabels[ind]);
        JTextField text = (JTextField) payPanel.getComponent(3);
        payPanel.remove(text);
        payPanel.add(bankPanel, 3);
        bankPanelButtonInit();
        refresh(mainPanel);
    }

    // EFFECTS: runs change credit limit panel
    public void runLimitPanel() {

    }

    // EFFECTS: creates an ActionListener for account input
    public ActionListener accountAction(String account) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountType = account;
                ind++;
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for bank input
    public ActionListener bankAction(String bankName) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountName1 = bankName;
                ind++;
            }
        };
        return al;
    }

    // EFFECTS: checks for button input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            ui.backClick();
        } else if (e.getSource() == payButton) {
            ui.payClick();
        } else if (e.getSource() == limitButton) {
            ui.limitClick();
        }
    }

    // GETTER
    public JPanel getPayPanel() {
        return payPanel;
    }

    // GETTER
    public JPanel getLimitPanel() {
        return limitPanel;
    }

}
