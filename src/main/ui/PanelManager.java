package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.json.JSONML;

import model.Account;

// Represents a panel manager that contains general methods
public class PanelManager {

    private static final String WARN = "***WARNING***";
    protected JButton[] buttonList;
    protected JButton backButton;
    protected int ind;
    protected JPanel mainPanel;
    protected MenuUI ui;
    private JPanel updatedPanel;
    private JLabel posNum;
    private JLabel posDec;
    private JLabel dec;
    private JLabel properString;

    // EFFECTS: initializes error labels
    public PanelManager(MenuUI ui) {
        this.ui = ui;
        initErrorLabel();
    }

    // MODIFIES: this
    // EFFECTS: initializes error labels
    private void initErrorLabel() {
        posNum = new JLabel("Please enter a positive number", JLabel.CENTER);
        posNum.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        posDec = new JLabel("Please enter a positive decimal amount", JLabel.CENTER);
        posDec.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        dec = new JLabel("Please enter a decimal amount", JLabel.CENTER);
        dec.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        properString = new JLabel("Please enter a string", JLabel.CENTER);
        properString.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    }

    // MODIFIES: JButton in list
    // EFFECTS: adds panel as ActionListener to each button
    protected void addAction(ActionListener panel, JButton[] list) {
        for (JButton j : list) {
            j.addActionListener(panel);
        }
    }

    // MODIFIES: panel
    // EFFECTS: refreshes panel for updates
    protected void refresh(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    // MODIFIES: panel, textField
    // EFFECTS: refreshes panel and sets text field focus
    protected void updateWithInput(JTextField text, JPanel panel) {
        refresh(panel);
        SwingUtilities.invokeLater(() -> {
            text.requestFocusInWindow();
        });
    }

    // MODIFIES: this
    // EFFECTS: creates an update panel with user input promp to continue program
    private void createUpdatePanel() {
        updatedPanel = new JPanel();
        updatedPanel.setLayout(new BoxLayout(updatedPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Updated!", JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        JButton cont = new JButton("Click to continue");
        cont.setAlignmentX(Component.CENTER_ALIGNMENT);
        updatedPanel.add(Box.createVerticalStrut(100));
        updatedPanel.add(label);
        updatedPanel.add(Box.createVerticalStrut(50));
        updatedPanel.add(Box.createVerticalStrut(50));
        updatedPanel.add(cont);
    }
    
    // REQUIRES: panel != null
    // EFFECTS: update screen after loading/saving
    protected void updatePersistenceScreen(JPanel panel) {
        createUpdatePanel();
        JButton cont = (JButton) updatedPanel.getComponent(4);
        cont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                ui.switchPanel(panel);
            }
        });
        if (panel == null) {
            ui.quitClick();
            System.exit(0);
        }
        refresh(updatedPanel);
        ui.switchPanel(updatedPanel);
    }

    // EFFECTS: update screen after editing banking information and checks for overdraft
    protected void updateScreen(JPanel panel, Account account) {
        createUpdatePanel();
        JButton cont = (JButton) updatedPanel.getComponent(4);
        cont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.switchPanel(panel);
            }
        });
        if (account != null) {
            checkOverDraft(account);
        }
        refresh(updatedPanel);
        ui.switchPanel(updatedPanel);
    }

    protected void updateTransferScreen(Account account1, Account account2) {
        createUpdatePanel();
        JButton cont = (JButton) updatedPanel.getComponent(4);
        cont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUpdatePanel();
                JButton cont = (JButton) updatedPanel.getComponent(4);
                cont.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ui.switchPanel(ui.getMenuPanel());
                    }
                });
                if (account2 != null) {
                    checkOverDraft(account2);
                }
                if (updatedPanel.getComponentCount() == 5) {
                    ui.switchPanel(ui.getMenuPanel());
                } else {
                    refresh(updatedPanel);
                    ui.switchPanel(updatedPanel);
                }  
            }
        });
        if (account1 != null) {
            checkOverDraft(account1);
        }
        refresh(updatedPanel);
        ui.switchPanel(updatedPanel);
    }

    // EFFECTS: creates bank input panel
    protected JPanel createBankPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        List<Account> bank = ui.getBanks();
        for (int i = 0; i < bank.size(); i++) {
            Account acc = bank.get(i);
            JButton button = new JButton(acc.getBank());
            button.setPreferredSize(new Dimension(100, 40));
            panel.add(button);
        }
        refresh(panel);
        return panel;
    }

    // EFFECTS: creates account input panel
    protected JPanel createAccountPanel() {
        JPanel panel = new JPanel();
        String[] temp = {"Chequeing", "Savings", "Credit"};
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (int i = 0; i < 3; i++) {
            JButton button = new JButton(temp[i]);
            button.setPreferredSize(new Dimension(100, 40));
            panel.add(button);
        }
        refresh(panel);
        return panel;
    }

    // EFFECTS: creates an input panel
    protected JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTextField text = new JTextField(40);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setMaximumSize(new Dimension(200, 40));
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        JButton submit = new JButton("Submit");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(100));
        panel.add(label); // 1
        panel.add(Box.createVerticalStrut(50));
        panel.add(text); // 3
        panel.add(Box.createVerticalStrut(25));
        panel.add(submit); // 5
        panel.add(Box.createVerticalStrut(25));
        panel.add(Box.createVerticalStrut(150));
        return panel;
    }

    // EFFECTS: creates a panel to show transactions and takes in a return to menu button
    protected JPanel createShowPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createEmptyBorder(50,0,75,0));
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        middlePanel.add(scrollPane);
        JLabel label = new JLabel("", JLabel.CENTER);
        bottomPanel.add(button);
        panel.add(label, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    // EFFECTS: creates a button that goes to a specific menu
    protected ActionListener createBackButton(JPanel panel) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.switchPanel(panel);
            }
        };
        return al;
    }

    // EFFECTS: checks of account overdraft
    private void checkOverDraft(Account account) {
        String s = account.getBank();
        double amount;
        if (account.checkOverdraftChequeing()) {
            amount = -account.getChequeing();
            check("Your " + s + " chequeing account is overdrafted by $", amount);
        }
        if (account.checkOverdraftSavings()) {
            amount = -account.getSavings();
            check("Your " + s + " savings account is overdrafted by $", amount);
        }
        if (account.checkOverLimit()) {
            amount = Math.round((account.getCredit() - account.getCreditLimit()) * 100.0) / 100.0;
            check("Your " + s + " credit account is overused by $", amount);
        }
    }

    // EFFECTS: checks chequeing account overdraft
    private void check(String s, double amount) {
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setText("<html><center>" + WARN + "<br>" + s + amount + "</center><html>");
        int i = updatedPanel.getComponentCount() - 2;
        updatedPanel.add(label, i);
        updatedPanel.add(Box.createVerticalStrut(50), i);
    }

    // EFFECTS: checks user input to make sure it is a positive integer
    protected int intText(String text, JPanel panel) {
        int num;
        try {
            num = Integer.parseInt(text);
            if (num > 0) {
                ind++;
                if (checkLastError(panel)) {
                    removeErrorLabel(panel);
                }
                return num;
            } else {
                if (!checkLastError(panel)) {
                    panel.add(posNum, panel.getComponentCount() - 2);
                } else {
                    removeErrorLabel(panel);
                    panel.add(posNum, panel.getComponentCount() - 2);
                }
            }
        } catch (NumberFormatException e) {
            if (!checkLastError(panel)) {
                panel.add(posNum, panel.getComponentCount() - 2);
            } else {
                removeErrorLabel(panel);
                panel.add(posNum, panel.getComponentCount() - 2);
            }
        }
        return 0;
    }

    // EFFECTS: checks user input to make sure it is a positive (or zero) double
    protected double doubleText(String text, JPanel panel) {
        double num;
        try {
            num = Double.parseDouble(text);
            if (num >= 0) {
                ind++;
                if (checkLastError(panel)) {
                    removeErrorLabel(panel);
                }
                return num;
            } else {
                if (!checkLastError(panel)) {
                    panel.add(posDec, panel.getComponentCount() - 2);
                } else {
                    removeErrorLabel(panel);
                    panel.add(posDec, panel.getComponentCount() - 2);
                }
            }
        } catch (NumberFormatException e) {
            if (!checkLastError(panel)) {
                panel.add(dec, panel.getComponentCount() - 2);
            } else {
                removeErrorLabel(panel);
                panel.add(dec, panel.getComponentCount() - 2);
            }
        }
        return 0.0;
    }

    // EFFECTS: checks user input to make sure it is a valid string
    protected String stringText(String text, JPanel panel) {
        try {
            Double.parseDouble(text);
            if (!checkLastError(panel)) {
                panel.add(properString, panel.getComponentCount() - 2);
            } else {
                removeErrorLabel(panel);
                panel.add(properString, panel.getComponentCount() - 2);
            }
        } catch (NumberFormatException e) {
            ind++;
            if (checkLastError(panel)) {
                removeErrorLabel(panel);
            }
            return text;
        }
        return "";
    }

    // EFFECTS: checks whether the third recent item added was a JLabel (error message)
    protected boolean checkLastError(JPanel panel) {
        return panel.getComponent(panel.getComponentCount() - 3) instanceof JLabel;
    }

    // MODIFIES: panel
    // EFFECTS: removes the latest error label
    protected void removeErrorLabel(JPanel panel) {
        panel.remove(panel.getComponent(panel.getComponentCount() - 3));
    }

    // GETTER
    protected JPanel getMainPanel() {
        return this.mainPanel;
    }

}
