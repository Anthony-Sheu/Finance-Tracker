package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Account;

// Represents a panel that has banking operations
public class BankPanel extends PanelManager implements ActionListener {

    private JButton addButton;
    private JButton balanceButton;
    private JButton returnToBankButton;
    private JLabel label;
    private GridBagConstraints gbc;
    private JPanel addBankPanel;
    private JPanel balancePanel;
    private Object[] bankHolder = {0.0, 0.0, 0.0, "", 0.0};
    private String[] bankLabels;

    // EFFECTS: initializes variables
    public BankPanel(MenuUI ui) {
        super(ui);
        buttonInit();
        labelInit();
        otherInit();
        panelInit();
        subPanelInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes main panel
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
    // EFFECTS: initializes sub-panels
    public void subPanelInit() {
        createBankInputPanel();
        balancePanel = createBalancePanel();
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to the main panel
    public void panelAddButtons() {
        gbc.gridwidth = 1;
        gbc.insets = new Insets(25, 5, 25, 5);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 0.1;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(addButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(balanceButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(backButton, gbc);
    }

    // MODIFIES: this
    // EFFECTS: creates the label for the bank panel
    public void labelInit() {
        label = new JLabel("BANKING MENU", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    public void buttonInit() {
        addButton = new JButton("Add new bank account");
        balanceButton = new JButton("See all bank balances");
        addButton.addActionListener(this);
        balanceButton.addActionListener(this);
        backButton = new JButton("Return to main menu");
        backButton.addActionListener(this);
        backButton.setPreferredSize(new Dimension(200, 40));
        returnToBankButton = new JButton("Return to bank menu");
        returnToBankButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes all other things
    public void otherInit() {
        bankLabels = createBankLabels();
    }

    // EFFECTS: creates bank prompts
    public String[] createBankLabels() {
        return new String[] {
            "What is the name of your banking institution?",
            "Please enter your chequeing balance",
            "Please enter your savings balance",
            "Please enter your credit balance",
            "Please enter your credit limit"
        };
    }

    // EFFECTS: initializes add bank menu
    public void createBankInputPanel() {
        addBankPanel = createInputPanel();
        JButton backButton = new JButton("Return to banking menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(createBackButton(mainPanel));
        addBankPanel.add(backButton);
    }

    // REQUIRES: bank.size() > 0
    // MODIFIES: this
    // EFFECTS: creates balance panel
    public JPanel createBalancePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Choose a bank below to view balances", JLabel.CENTER);
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(returnToBankButton);
        panel.add(label, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    // EFFECTS: runs balance panel
    public void runBalancePanel() {
        JPanel middlePanel = (JPanel) balancePanel.getComponent(1);
        middlePanel.removeAll();
        List<Account> bank = ui.getBanks();
        for (int i = 0; i < bank.size(); i++) {
            Account acc = bank.get(i);
            JButton button = new JButton(acc.getBank());
            button.setPreferredSize(new Dimension(100, 40));
            JPanel tempPanel = createBalanceSubPanel(acc);
            button.addActionListener(createBackButton(tempPanel));
            middlePanel.add(button);
        }
        refresh(balancePanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for an individual bank account
    public JPanel createBalanceSubPanel(Account acc) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createEmptyBorder(50,0,100,0));
        JTextArea textArea = new JTextArea();
        textArea.setText(acc.printAccount());
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        middlePanel.add(textArea);
        JButton returnToBalanceButton = new JButton("Return");
        returnToBalanceButton.addActionListener(this);
        bottomPanel.add(returnToBalanceButton);
        returnToBalanceButton.addActionListener(backToBalanceAction(returnToBalanceButton));
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: runs add banking information panel
    public void runAddBankPanel() {
        ind = 0;
        createBankInputPanel();
        JButton submit = (JButton) addBankPanel.getComponent(5);
        JLabel label = (JLabel) addBankPanel.getComponent(1);
        JTextField text = (JTextField) addBankPanel.getComponent(3);
        label.setText(bankLabels[0]);
        submit.addActionListener(addBankAction(text, label));
        updateWithInput(text, addBankPanel);
    }

    // MODIFIES: this
    // EFFECTS: returns to balance viewing panel
    public ActionListener backToBalanceAction(JButton button) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    ui.switchPanel(balancePanel);
                }
            }
        };
        return al;
    }

    // MODIFIES: this
    // EFFECTS: creates an ActionListener for add bank panel
    public ActionListener addBankAction(JTextField text, JLabel label) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ind == 0) {
                    bankHolder[ind] = stringText(text.getText(), addBankPanel);
                } else if (1 <= ind && ind <= 4) {
                    bankHolder[ind] = doubleText(text.getText(), addBankPanel);
                }
                text.setText("");
                if (ind < 5) {
                    label.setText(bankLabels[ind]);
                } else {
                    changeUIBank(bankHolder);
                    ui.backClick();
                }
                updateWithInput(text, addBankPanel);
            }
        };
        return al;
    }
    
    // MODIFIES: MenuUI
    // EFFECTS: changes the values in MenuUI to update
    public void changeUIBank(Object[] temp) {
        ui.setBankName((String) temp[0]);
        ui.setCheq((double) temp[1]);
        ui.setSave((double) temp[2]);
        ui.setCred((double) temp[3]);
        ui.setCredLim((double) temp[4]);
        ui.addBank();
    }   

    // EFFECTS: checks which button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            ui.addBankClick();
        } else if (e.getSource() == balanceButton) {
            ui.bankBalanceClick();
        } else if (e.getSource() == backButton) {
            ui.backClick();
        } else if (e.getSource() == returnToBankButton) {
            ui.bankClick();
        }
    }

    // GETTER
    public JPanel getAddBankPanel() {
        return addBankPanel;
    }

    // GETTER
    public JPanel getBalancePanel() {
        return balancePanel;
    }

}
