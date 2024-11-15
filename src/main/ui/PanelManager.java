package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
        return panel;
    }

    // EFFECTS: creates a panel to show transactions and takes in a return to menu button
    public JPanel createShowPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createEmptyBorder(50,0,100,0));
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

    // EFFECTS: creates a button that returns to transaction menu
    public ActionListener createBackButton(JPanel panel, JButton button) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    ui.switchPanel(panel);
                }
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
                    removeLastComponent(panel);
                }
                return num;
            } else {
                if (!checkLastError(panel)) {
                    panel.add(posNum);
                } else {
                    removeLastComponent(panel);
                    panel.add(posNum);
                }
            }
        } catch (NumberFormatException e) {
            if (!checkLastError(panel)) {
                panel.add(posNum);
            } else {
                removeLastComponent(panel);
                panel.add(posNum);
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
                    removeLastComponent(panel);
                }
                return num;
            } else {
                if (!checkLastError(panel)) {
                    panel.add(posDec);
                } else {
                    removeLastComponent(panel);
                    panel.add(posDec);
                }
            }
        } catch (NumberFormatException e) {
            if (!checkLastError(panel)) {
                panel.add(dec);
            } else {
                removeLastComponent(panel);
                panel.add(dec);
            }
        }
        return 0.0;
    }

    // EFFECTS: checks user input to make sure it is a valid string
    protected String stringText(String text, JPanel panel) {
        double dec;
        try {
            dec = Double.parseDouble(text);
            if (!checkLastError(panel)) {
                panel.add(properString);
            } else {
                removeLastComponent(panel);
                panel.add(properString);
            }
        } catch (NumberFormatException e) {
            ind++;
            if (checkLastError(panel)) {
                removeLastComponent(panel);
            }
            return text;
        }
        return "";
    }

    // EFFECTS: checks whether the most recent item added was a JLabel (error message)
    public boolean checkLastError(JPanel panel) {
        return panel.getComponent(panel.getComponentCount() - 1) instanceof JLabel;
    }

    public void removeLastComponent(JPanel panel) {
        panel.remove(panel.getComponent(panel.getComponentCount() - 1));
    }

    // GETTER
    protected JPanel getMainPanel() {
        return this.mainPanel;
    }

}
