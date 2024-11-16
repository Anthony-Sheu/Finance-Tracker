package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import model.Account;

// Represents a panel manager that contains general methods
public class PanelManager {

    protected JButton[] buttonList;
    protected JButton backButton;
    protected int ind;
    protected JPanel mainPanel;
    protected MenuUI ui;
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
