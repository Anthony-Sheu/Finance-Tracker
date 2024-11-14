package ui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;

// represents user interface for main menu
public class MenuUI extends Menu implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    JFrame frame;
    JPanel panel;
    JButton transactionsButton, bankButton, creditButton, transferButton, quitButton;
    JLabel label;
    GridBagConstraints gbc;

    public MenuUI() {
        super();
        super.init();
        init();
    }

    // EFFECTS: initializes main frame and panel
    public void init() {
        frameInit();
        buttonInit();
        panelInit();
        frame.add(panel);
        frame.setVisible(true);
    }

    public void frameInit() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Finance Tracker GUI");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void buttonInit() {
        transactionsButton = new JButton("Transactions");
        transactionsButton.addActionListener(this);
        bankButton = new JButton("Bank accounts");
        bankButton.addActionListener(this);
        creditButton = new JButton("Credit card");
        creditButton.addActionListener(this);
        transferButton = new JButton("Transfer between accounts");
        transferButton.addActionListener(this);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
    }

    public void panelInit() {
        panel = new JPanel();
        label = new JLabel("MAIN MENU", JLabel.CENTER);
        panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);
        // gbc.weighty = 0;
        // gbc.gridx = 0;
        // gbc.gridy = 0;
        // 
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // panel.add(label, gbc);
        panelAddButtons();
    }

    public void panelAddButtons() {
        int[][] temp = {{0, 1, 0, 1}, {1, 1, 2, 2}};
        JButton[] btemp = {transactionsButton, bankButton, creditButton, transferButton};
        gbc.gridwidth = 1;
        gbc.insets = new Insets(25, 5, 25, 5);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 0.1;
        gbc.weightx = 1;
        for (int i = 0; i < 4; i++) {
            gbc.gridx = temp[0][i];
            gbc.gridy = temp[1][i];
            panel.add(btemp[i], gbc);
        }
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        quitButton.setPreferredSize(new Dimension(100, 40));
        panel.add(quitButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == transactionsButton) {
            System.out.println("tran");
        } else if (e.getSource() == bankButton) {

        } else if (e.getSource() == creditButton) {
            
        } else if (e.getSource() == transferButton) {
            
        } else if (e.getSource() == quitButton) {
            
        }
    }

}
