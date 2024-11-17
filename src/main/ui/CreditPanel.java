package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Represents a panel for credit methods
public class CreditPanel extends PanelManager implements ActionListener {

    private GridBagConstraints gbc;
    private JLabel label;
    private JButton payButton;
    private JButton limitButton;
    private JPanel payPanel;
    private JPanel limitPanel;

    // constructor
    public CreditPanel(MenuUI ui) {
        super(ui);
        buttonInit();
        labelInit();
        subPanelInit();
        panelInit();
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
