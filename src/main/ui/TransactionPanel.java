package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Represents a transaction menu
public class TransactionPanel extends PanelManager implements ActionListener {

    private JPanel mainPanel;
    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel editPanel;
    private JPanel showAllPanel;
    private JPanel showInDatePanel;
    private JPanel showExpPanel;
    private JPanel showAllExpPanel;
    private JPanel refundPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton showAllButton;
    private JButton backButton;
    private JButton showInDateButton;
    private JButton showExpButton;
    private JButton showAllExpButton;
    private JButton refundButton;
    JLabel label;
    private MenuUI ui;
    int ind;

    // EFFECTS: intializes variables
    public TransactionPanel(MenuUI ui) {
        buttonInit();
        panelInit();
        subPanelInit();
        this.ui = ui;
    }

    public void buttonInit() {
        addButton = new JButton("Add a transaction");
        removeButton = new JButton("<html><center>Remove<br>a transaction</center></html>");
        editButton = new JButton("<html><center>Edit a<br>previous transaction</center><html>");
        showAllButton = new JButton("<html><center>Show all<br>transactions</center><html>");
        showInDateButton = new JButton("<html><center>Show transactions<br>within a date/year</center><html>");
        showExpButton = new JButton("<html><center>Show all spendings<br>in an expense category</center><html>");
        showAllExpButton = new JButton("<html><center>Show spendings in<br>all expense categories</center><html>");
        refundButton = new JButton("Refund a transaction");
        backButton = new JButton("Return to main menu");
        buttonList = new JButton[] {
            addButton, removeButton, editButton, showAllButton, showInDateButton,
            showExpButton, showAllExpButton, refundButton, backButton
        };
        addAction(this, buttonList);
    }

    public void panelInit() {
        label = new JLabel("TRANSACTION MENU", JLabel.CENTER);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 4));
        mainPanel.add(label);
        for (JButton j : buttonList) {
            mainPanel.add(j);
        }
    }

    public void subPanelInit() {
        createAddPanel();
        removePanel = new JPanel();
        editPanel = new JPanel();
        showAllPanel = new JPanel();
        showInDatePanel = new JPanel();
        showExpPanel = new JPanel();
        showAllExpPanel = new JPanel();
        refundPanel = new JPanel();
    }

    public void createAddPanel() {
        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        ind = 0;
        Object[] temp = {0, 0, 0, 0, "", "", "", "", ""};
        JTextField text = new JTextField(40);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setMaximumSize(new Dimension(200, 40));
        JLabel l = new JLabel("What is the number month of your transaction?", JLabel.CENTER);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp[ind] = text.getText();
                ind++;
                addPanelChangeLabel(ind, l);
            }
        });
        addPanel.add(Box.createVerticalStrut(100));
        addPanel.add(l);
        addPanel.add(Box.createVerticalStrut(50));
        addPanel.add(text);
        addPanel.add(Box.createVerticalStrut(25));
        addPanel.add(submit);
        changeUITransaction(temp);
    }

    public void addPanelChangeLabel(int i, JLabel l) {
        if (i == 1) {
            l.setText("What is the date of your transaction?");
        } else if (i == 2) {
            l.setText("What was the year of your transaction?");
        } else if (i == 3) {
            l.setText("What is the amount spent?");
        } else if (i == 4) {
            l.setText("What is the name of the store/vendor?");
        } else if (i == 5) {
            l.setText("What is the expense category?");
        } else if (i == 6) {
            l.setText("Any notes? (press enter to skip)");
        } else if (i == 7) {
            l.setText("What is the account name?");
        } else if (i == 8) {
            l.setText("What is the account type?\nChequeing, Savings, Credit");
        }
    }

    public void changeUITransaction(Object[] temp) {
        ui.setMonth((int) temp[0]);
        ui.setDate((int) temp[1]);
        ui.setYear((int) temp[2]);
        ui.setAmount((double) temp[3]);
        ui.setStore((String) temp[4]);
        ui.setExpense((String) temp[5]);
        ui.setNote((String) temp[6]);
        ui.setAccountName((String) temp[7]);
        ui.setAccountType((String) temp[8]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            ui.backClick();
        } else if (e.getSource() == addButton) {
            ui.addTransactionClick();
        } else if (e.getSource() == removeButton) {
            
        } else if (e.getSource() == editButton) {
            
        } else if (e.getSource() == showAllButton) {
            
        } else if (e.getSource() == showInDateButton) {
            
        } else if (e.getSource() == showExpButton) {
            
        } else if (e.getSource() == showAllButton) {
            
        } else {

        }
    }

    // GETTER
    public JPanel getAddPanel() {
        return addPanel;
    }
    
    // GETTER
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
