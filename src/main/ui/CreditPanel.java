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

import org.json.JSONML;

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
    private JPanel amountPanel;
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
    public void payPanelSubPanelInit() {
        bankPanel = createBankPanel();
        accPanel = createAccountPanel();
        payPanelAccButtonInit();
        amountPanel = createInputPanel();
        payPanelAmountButtonInit();
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
    // EFFECTS: initializes limit panel
    public void createLimitPanel() {
        limitPanel = createInputPanel();
        bankPanel = createBankPanel();
        amountPanel = createInputPanel();
        limitPanelBankButtonInit();
        limitPanelAmountButtonInit();
        JButton button = new JButton("Return to credit menu");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.creditClick();
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        limitPanel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: initializes bank buttons on bank panel
    public void payPanelBankButtonInit() {
        List<Account> bank = ui.getBanks();
        for (int i = 0; i < bank.size(); i++) {
            JButton button =  (JButton) bankPanel.getComponent(i);
            button.addActionListener(payPanelBankAction(button.getText()));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes account buttons on acc panel
    public void payPanelAccButtonInit() {
        JButton credit = (JButton) accPanel.getComponent(2);
        accPanel.remove(credit);
        for (int i = 0; i < 2; i++) {
            JButton button = (JButton) accPanel.getComponent(i);
            button.addActionListener(accountAction(button.getText()));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes submit button on amount panel
    public void payPanelAmountButtonInit() {
        JTextField text = (JTextField) amountPanel.getComponent(3);
        JButton submit = (JButton) amountPanel.getComponent(5);
        submit.addActionListener(payPanelAmountAction(text));
        JLabel label = (JLabel) amountPanel.getComponent(1);
        label.setText("How much would you like to pay?");
        JButton button = new JButton("Return to credit menu");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.creditClick();
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        amountPanel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: initializes bank buttons on bank panel
    public void limitPanelBankButtonInit() {
        List<Account> bank = ui.getBanks();
        for (int i = 0; i < bank.size(); i++) {
            JButton button =  (JButton) bankPanel.getComponent(i);
            button.addActionListener(limitPanelBankAction(button.getText()));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes submit button on amount panel
    public void limitPanelAmountButtonInit() {
        JTextField text = (JTextField) amountPanel.getComponent(3);
        JButton submit = (JButton) amountPanel.getComponent(5);
        submit.addActionListener(limitPanelAmountAction(text));
        JLabel label = (JLabel) amountPanel.getComponent(1);
        label.setText("How much is your new credit card limit?");
        JButton button = new JButton("Return to credit menu");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.creditClick();
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        amountPanel.add(button);
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
        JButton button = new JButton("Return to credit menu");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.creditClick();
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        payPanel.add(button);
    }

    // EFFECTS: runs pay panel
    public void runPayPanel() {
        ind = 0;
        payPanelSubPanelInit();
        createPayPanel();
        JLabel label = (JLabel) payPanel.getComponent(1);
        label.setText(payLabels[ind]);
        JTextField text = (JTextField) payPanel.getComponent(3);
        payPanel.remove(text);
        payPanel.add(bankPanel, 3);
        JButton submit = (JButton) payPanel.getComponent(5);
        payPanel.remove(submit);
        payPanelBankButtonInit();
        refresh(payPanel);
    }

    // EFFECTS: runs account panel
    public void runAccPanel() {
        JLabel label = (JLabel) payPanel.getComponent(1);
        label.setText(payLabels[ind]);
        payPanel.remove(bankPanel);
        payPanel.add(accPanel, 3);
        refresh(payPanel);
    }

    // EFFECTS: runs amount panel
    public void runAmountPanel() {
        JTextField text = (JTextField) amountPanel.getComponent(3);
        updateWithInput(text, amountPanel);
        ui.switchPanel(amountPanel);
    }

    // EFFECTS: runs change credit limit panel
    public void runLimitPanel() {
        createLimitPanel();
        JLabel label = (JLabel) limitPanel.getComponent(1);
        label.setText("What account would you like to change the limit of?");
        limitPanel.remove(limitPanel.getComponent(3));
        limitPanel.add(bankPanel, 3);
        limitPanel.remove(limitPanel.getComponent(5));
        refresh(limitPanel);
    }

    // EFFECTS: runs limit amount panel
    public void runLimitAmountPanel() {
        JTextField text = (JTextField) amountPanel.getComponent(3);
        updateWithInput(text, amountPanel);
        ui.switchPanel(amountPanel);
    }

    // MODIFIES: MenuUI
    // EFFECTS: pays credit card
    public void payCredit() {
        ui.payCreditCard(accountName1, accountName2, accountType, amount);
        if (accountName1.equals(accountName2)) {
            updateScreen(mainPanel, ui.getBank().findAccount(accountName1));
        } else {
            updateTransferScreen(ui.getBank().findAccount(accountName1), ui.getBank().findAccount(accountName2));
        }
    }

    // MODIFIES: MenuUI
    // EFFECTS: changes credit card limit() 
    public void changeLimit() {
        ui.changeCreditLimit(accountName1, amount);
        updateScreen(mainPanel, ui.getAccount());
    }

    // EFFECTS: creates an ActionListener for bank input
    public ActionListener limitPanelBankAction(String bankName) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountName1 = bankName;
                runLimitAmountPanel();
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for account input
    public ActionListener limitPanelAmountAction(JTextField text) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                amount = doubleText(text.getText(), amountPanel);
                text.setText("");
                if (ind != 0) {
                    changeLimit();
                } else {
                    updateWithInput(text, amountPanel);
                }
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for account input
    public ActionListener payPanelAmountAction(JTextField text) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                amount = doubleText(text.getText(), amountPanel);
                text.setText("");
                if (ind != 0) {
                    payCredit();
                } else {
                    updateWithInput(text, amountPanel);
                }
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for account input
    public ActionListener accountAction(String account) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountType = account;
                ind++;
                runAmountPanel();
                refresh(payPanel);
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener for bank input
    public ActionListener payPanelBankAction(String bankName) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ind == 0) {
                    accountName1 = bankName;
                } else if (ind == 1)
                    accountName2 = bankName;  
                ind++;
                JLabel label = (JLabel) payPanel.getComponent(1);
                label.setText(payLabels[ind]);
                if (ind > 1) {
                    runAccPanel();
                }
                refresh(payPanel);
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
