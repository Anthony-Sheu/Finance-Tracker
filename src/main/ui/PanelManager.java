package ui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

// Represents a panel manager that contains general methods
public class PanelManager {

    protected JButton[] buttonList;

    public PanelManager() {}

    protected void addAction(ActionListener panel, JButton[] list) {
        for (JButton j : list) {
            j.addActionListener(panel);
        }
    }

    protected void refresh(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

}
