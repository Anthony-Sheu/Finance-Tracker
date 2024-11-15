package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Represents a panel manager that contains general methods
public class PanelManager {

    protected JButton[] buttonList;
    protected int ind;
    private JLabel posNum;
    private JLabel posDec;
    private JLabel dec;
    private JLabel properString;

    public PanelManager() {
        initErrorLabel();
    }

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

    protected void addAction(ActionListener panel, JButton[] list) {
        for (JButton j : list) {
            j.addActionListener(panel);
        }
    }

    protected void refresh(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

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

    protected double doubleText(String text, JPanel panel) {
        double num;
        try {
            num = Double.parseDouble(text);
            if (num > 0) {
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

}
