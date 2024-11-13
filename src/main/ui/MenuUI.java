package ui;

import javax.swing.*;
import java.awt.event.*;

// represents user interface for main menu
public class MenuUI extends Menu implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    JFrame frame;

    public MenuUI() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
