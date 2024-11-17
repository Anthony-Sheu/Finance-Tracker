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

// Represents a main menu panel
public class MenuPanel extends PanelManager implements ActionListener {

    private JPanel mainPanel;
    private JButton transactionsButton, bankButton, creditButton, transferButton, quitButton;
    private JLabel label;
    private GridBagConstraints gbc;
    
    // EFFECTS: intializes variables
    public MenuPanel(MenuUI ui) {
        super(ui);
        buttonInit();
        panelInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes panel
    public void panelInit() {
        mainPanel = new JPanel();
        label = new JLabel("MAIN MENU", JLabel.CENTER);
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
    // EFFECTS: adds buttons to panel
    public void panelAddButtons() {
        int[][] temp = {{0, 1, 0, 1}, {1, 1, 2, 2}};
        gbc.gridwidth = 1;
        gbc.insets = new Insets(25, 5, 25, 5);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 0.1;
        gbc.weightx = 1;
        for (int i = 0; i < 4; i++) {
            gbc.gridx = temp[0][i];
            gbc.gridy = temp[1][i];
            mainPanel.add(buttonList[i], gbc);
        }
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        quitButton.setPreferredSize(new Dimension(100, 40));
        mainPanel.add(quitButton, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    public void buttonInit() {
        transactionsButton = new JButton("Transactions");
        bankButton = new JButton("Bank accounts");
        creditButton = new JButton("Credit card");
        transferButton = new JButton("Transfer between accounts");
        quitButton = new JButton("Quit");
        buttonList = new JButton[] {
            transactionsButton, bankButton, creditButton, transferButton, quitButton
        };
        addAction(this, buttonList);
    }

    // EFFECTS: decides which sub menu user goes to 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == transactionsButton) {
            ui.transactionClick();
        } else if (e.getSource() == bankButton) {
            ui.bankClick();
        } else if (e.getSource() == creditButton) {
            ui.creditClick();
        } else if (e.getSource() == transferButton) {
            ui.transferClick();
        } else if (e.getSource() == quitButton) {
            ui.quitClick();
        }
    }

    // GETTER
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
