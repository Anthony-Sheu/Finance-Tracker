package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Represents a panel that prompts user yes/no if they would like to save/load information
public class PersistencePanel extends PanelManager implements ActionListener {

    private JPanel middlePanel;
    private JPanel errorPanel;
    private JLabel label;
    private JButton yes;
    private JButton no;

    // CONSTRUCTOR
    public PersistencePanel(MenuUI ui) {
        super(ui);
        panelInit();
        createErrorPanel();
        backButtonInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes yes/no panel
    public void panelInit() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        label = new JLabel("", JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        middlePanelInit();
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(label); // 1
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(middlePanel); // 3
        mainPanel.add(Box.createVerticalStrut(200));
    }

    // MODIFIES: this
    // EFFECTS: adds yes/no button to middle panel
    public void middlePanelInit() {
        yes = new JButton("Yes");
        yes.setPreferredSize(new Dimension(100, 40));
        no = new JButton("No");
        no.setPreferredSize(new Dimension(100, 40));
        middlePanel.add(yes);
        middlePanel.add(no);
    }

    // MODIFIES: this
    // EFFECTS: initializes yes and no button
    public void yesNoButtonInit() {
        middlePanel.remove(yes);
        middlePanel.remove(no);
        middlePanelInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes back button
    public void backButtonInit() {
        backButton = new JButton("Return to main menu");
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(200, 40));
    }

    // MODIFIES: this
    // EFFECTS: creates an error panel
    public void createErrorPanel() {
        errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("There was an error with loading/saving", JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        JButton cont = new JButton("Continue");
        cont.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorPanel.add(Box.createVerticalStrut(100));
        errorPanel.add(label);
        errorPanel.add(Box.createVerticalStrut(200));
        errorPanel.add(cont);
    }

    // MODIFIES: this
    // EFFECTS: runs error panel
    public void runErrorPanel(JPanel panel) {
        JButton cont = (JButton) errorPanel.getComponent(3);
        cont.addActionListener(errorAction(panel));
        ui.switchPanel(errorPanel);
    }

    // MODIFIES: this
    // EFFECTS: prompt load banking info
    public void runBankLoad() {
        yesNoButtonInit();
        label.setText("You currently have saved banking information, would you like to load it?");
        yes.addActionListener(bankLoadAction(true));
        no.addActionListener(bankLoadAction(false));
        refresh(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: prompt load transaction info
    public void runTranLoad() {
        yesNoButtonInit();
        label.setText("You currently have saved transaction information, would you like to load it?");
        yes.addActionListener(tranLoadAction(true));
        no.addActionListener(tranLoadAction(false));
        refresh(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: prompt load banking info
    public void runBankSave() {
        yesNoButtonInit();
        label.setText("Would you like to save your banking information?");
        yes.addActionListener(bankSaveAction(true));
        no.addActionListener(bankSaveAction(false));
        refresh(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: prompt load transaction info
    public void runTranSave() {
        yesNoButtonInit();
        label.setText("Would you like to save your transaction information?");
        yes.addActionListener(tranSaveAction(true));
        no.addActionListener(tranSaveAction(false));
        refresh(mainPanel);
    }

    // EFFECTS: continues program
    public ActionListener errorAction(JPanel panel) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panel == null) {
                    ui.quitClick();
                    System.exit(0);
                } else {
                    ui.switchPanel(panel);
                }
            }
        };
        return al;
    }

    // MODIFIES: this
    // EFFECTS: checks if user wants to load transaction information
    public ActionListener tranLoadAction(Boolean bool) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.setTranLoad(bool);
                ui.load();
                if (ui.getLoadError()) {
                    runErrorPanel(ui.getMenuPanel());
                } else {
                   updatePersistenceScreen(ui.getMenuPanel()); 
                }
            }
        };
        return al;
    }

    // MODIFIES: this
    // EFFECTS: checks if user wants to load transaction information
    public ActionListener tranSaveAction(Boolean bool) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.setTranSave(bool);
                ui.save();
                if (ui.getSaveError()) {
                    runErrorPanel(null);
                } else {
                    updatePersistenceScreen(null);
                }   
            }
        };
        return al;
    }

    // MODIFIES: this
    // EFFECTS: checks if user wants to load banking information
    public ActionListener bankLoadAction(Boolean bool) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.setBankLoad(bool);
                if (bool == true) {
                    runTranLoad();
                } else {
                    ui.addBankClick();
                }
            }
        };
        return al;
    }

    // MODIFIES: this
    // EFFECTS: checks if user wants to load banking information
    public ActionListener bankSaveAction(Boolean bool) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.setBankSave(bool);
                if (bool == true) {
                    runTranSave();
                } else {
                    updatePersistenceScreen(null);
                }
            }
        };
        return al;
    }

    // EFFECTS: checks for button input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            ui.backClick();
        }
    }

    // GETTER
    public JPanel getErrorPanel() {
        return errorPanel;
    }

}
