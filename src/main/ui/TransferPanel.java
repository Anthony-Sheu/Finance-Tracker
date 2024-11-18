package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Represents a panel that transfer amounts between accounts
public class TransferPanel extends PanelManager implements ActionListener {

    private JPanel accPanel;
    private JPanel bankPanel;
    private JPanel amountPanel;
    private String accountName1;
    private String accountName2;
    private String accountType1;
    private String accountType2;
    private double amount;
    private String[] labels;
    
    // EFFECTS: initializes fields
    public TransferPanel(MenuUI ui) {
        super(ui);
        buttonInit();
        labelInit();
        panelInit();
        subPanelInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    public void buttonInit() {
        backButton = new JButton("Return to main menu");
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: initializes labels
    public void labelInit() {
        labels = new String[] {
            "What is the first account you would like to transfer out of?",
            "What is the account type? (Credit is not allowed)",
            "What is the second account you would like to transfer into?",
            "What is the account type? (Credit is not allowed)",
            "What is the amount you would like to transfer?",
        };
    }

    // MODIFIES: this
    // EFFECTS: initializes main panel
    public void panelInit() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("");
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel middlePanel = new JPanel();
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(label); // 1
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(middlePanel); // 3
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(backButton);
        mainPanel.add(Box.createVerticalStrut(50));
    }

    // MODIFIES: this
    // EFFECTS: initializes account and bank panel
    public void subPanelInit() {
        accPanel = createAccountPanel();
        accPanelButtonInit();
        bankPanel = createBankPanel();
        bankPanelButtonInit();
        amountPanel = createAmountPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates amount input panel
    public JPanel createAmountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTextField text = new JTextField(40);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setMaximumSize(new Dimension(200, 40));
        JButton submit = new JButton("Submit");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.addActionListener(submitAction(text));
        panel.add(Box.createVerticalStrut(100));
        panel.add(text);
        panel.add(Box.createVerticalStrut(25));
        panel.add(submit);
        panel.add(Box.createVerticalStrut(25));
        return panel;
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
        JButton credit = (JButton) accPanel.getComponent(2);
        accPanel.remove(credit);
        for (int i = 0; i < accPanel.getComponentCount(); i++) {
            JButton button = (JButton) accPanel.getComponent(i);
            button.addActionListener(accountAction(button));
        }
    }

    // MODIFIES: this
    // EFFECTS: runs transfer panel
    public void runTransferPanel() {
        bankPanel = createBankPanel();
        bankPanelButtonInit();
        JLabel label = (JLabel) mainPanel.getComponent(1);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        JPanel middlePanel = (JPanel) mainPanel.getComponent(3);
        mainPanel.remove(middlePanel);
        mainPanel.add(bankPanel, 3);
        ind = 0;
        label.setText(labels[0]);
        refresh(mainPanel);
    }

    // MODIFIES: MenuUI
    // EFFECTS: transfer amount between two accounts
    public void runTransfer() {
        ui.transfer(accountName1, accountType1, accountName2, accountType2, amount);
    }

    // MODIFIES: this
    // EFFECTS: moves onto next input panel
    public void nextPanel() {
        JPanel middlePanel = (JPanel) mainPanel.getComponent(3);
        JLabel label = (JLabel) mainPanel.getComponent(1);
        mainPanel.remove(middlePanel);
        ind++;
        label.setText(labels[ind]);
        if (ind == 0 || ind == 2) {
            mainPanel.add(bankPanel, 3);
        } else if (ind == 1 || ind == 3) {
            mainPanel.add(accPanel, 3);
        } else {
            mainPanel.add(amountPanel, 3);
        }
        refresh(mainPanel);
    }

    // EFFECTS: creates an ActionListener to check bank name input
    public ActionListener bankAction(JButton button) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ind == 0) {
                    accountName1 = button.getText();
                    nextPanel();
                } else if (ind == 2) {
                    accountName2 = button.getText();
                    nextPanel();
                }
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener to check account name input
    public ActionListener accountAction(JButton button) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ind == 1) {
                    accountType1 = button.getText();
                    nextPanel();
                } else if (ind == 3) {
                    accountType2 = button.getText();
                    nextPanel();
                }
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener to take in double input
    public ActionListener submitAction(JTextField text) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                amount = doubleText(text.getText(), mainPanel);
                text.setText("");
                updateWithInput(text, mainPanel);
                if (ind != 0) {
                    runTransfer();
                    if (accountName1.equals(accountName2)) {
                        updateScreen(ui.getMenuPanel(), ui.getBank().findAccount(accountName1));
                    } else {
                        updateTransferScreen(ui.getBank().findAccount(accountName1), ui.getBank().findAccount(accountName2));
                    }
                }
            }
        };
        return al;
    }

    // EFFECTS: checks button action
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            ui.backClick();
        }
    }
}
