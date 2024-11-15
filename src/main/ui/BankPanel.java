package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Represents a panel that has banking operations
public class BankPanel extends PanelManager implements ActionListener {

    private JButton addButton;
    private JButton balanceButton;
    private JLabel label;
    private GridBagConstraints gbc;
    private JPanel addBankPanel;
    private Object[] bankHolder = {0.0, 0.0, 0.0, "", 0.0};
    private String[] bankLabels;

    // EFFECTS: initializes variables
    public BankPanel(MenuUI ui) {
        buttonInit();
        labelInit();
        subPanelInit();
        otherInit();
        panelInit();
        this.ui = ui;
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
        addBankPanel = createInputPanel();
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

    // MODIFIES: this
    // EFFECTS: runs add banking information panel
    public void runAddBankPanel() {
        runPanel(addBankPanel, bankLabels[0]);
        JButton submit = (JButton) addBankPanel.getComponent(5);
        submit.addActionListener(addBankAction());
    }

    // MODIFIES: this
    // EFFECTS: creates an ActionListener for add bank panel
    public ActionListener addBankAction() {
        JTextField text = (JTextField) addBankPanel.getComponent(3);
        JLabel label = (JLabel) addBankPanel.getComponent(1);
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ind == 0) {
                    bankHolder[ind] = stringText(text.getText(), addBankPanel);
                } else if (1 <= ind && ind <= 4) {
                    bankHolder[ind] = doubleText(text.getText(), addBankPanel);
                }
                if (ind < 5) {
                    label.setText(bankLabels[ind]);
                    text.setText("");
                } else {
                    changeUITransaction(bankHolder);
                    ui.backClick();
                }
                refresh(addBankPanel);
                SwingUtilities.invokeLater(() -> {
                    text.requestFocusInWindow();
                });
            }
        };
        return al;
    }
    
    // MODIFIES: MenuUI
    // EFFECTS: changes the values in MenuUI to update
    public void changeUITransaction(Object[] temp) {
        ui.setBankName((String) temp[0]);
        ui.setCheq((double) temp[1]);
        ui.setSave((double) temp[2]);
        ui.setCred((double) temp[3]);
        ui.setCredLim((double) temp[4]);
    }

    // EFFECTS: checks which button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            ui.addBankClick();
        } else if (e.getSource() == balanceButton) {

        } else if (e.getSource() == backButton) {
            ui.backClick();
        }
    }

    // GETTER
    public JPanel getAddBankPanel() {
        return addBankPanel;
    }

}
