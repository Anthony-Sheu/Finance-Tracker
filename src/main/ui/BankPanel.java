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

    JButton addButton;
    JButton balanceButton;
    JLabel label;
    GridBagConstraints gbc;
    JPanel addBankPanel;

    // EFFECTS: initializes variables
    public BankPanel(MenuUI ui) {
        buttonInit();
        labelInit();
        subPanelInit();
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
    // EFFECTS: runs add banking information panel
    public void runAddBankPanel() {
        JButton submit = (JButton) addBankPanel.getComponent(5);
        JLabel label = (JLabel) addBankPanel.getComponent(1);
        JTextField text = (JTextField) addBankPanel.getComponent(3);
        ind = 0;
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
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
