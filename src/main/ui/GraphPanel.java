package ui;

import javax.swing.*;

import model.Transaction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

// Represents a panel that displays a bar graph of monthl spendings per a selected year
public class GraphPanel extends PanelManager implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private static final int TOP_MARGIN = 50;
    private static final int BOTTOM_MARGIN = 80;
    private static final int LEFT_MARGIN = 150;
    private static final int RIGHT_MARGIN = 30;
    private static final int GRAPH_WIDTH = WIDTH - LEFT_MARGIN - RIGHT_MARGIN;
    private static final int GRAPH_HEIGHT = HEIGHT - TOP_MARGIN - BOTTOM_MARGIN;
    private static final int TICK_COUNT = 10; 
    private static final int TICK_HEIGHT = GRAPH_HEIGHT / TICK_COUNT;
    private static final int BAR_WIDTH = GRAPH_WIDTH / 12;
    private static final Color BAR_COLOR = Color.BLUE;
    private static final String[] MONTHS = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", 
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    private int[] spending = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    private int yearToGraph;
    private JPanel middlePanel;
    private JPanel yearPanel;
    private int maxY;
    private int tickSpacing;
    
    // EFFECTS: constructs graph panel
    public GraphPanel(MenuUI ui) {
        super(ui);
        buttonInit();
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    public void buttonInit() {
        backButton = new JButton("Return to year menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes main panel which will prompt user about which year they
    // wish to view
    public void panelInit() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        middlePanel = new JPanel() {
            // EFFECTS: overrides paintComponent
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setPreferredSize(new Dimension(700, 500));
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that displays years as buttons
    public void createYearPanel() {
        yearPanel = new JPanel();
        yearPanel.setLayout(new BoxLayout(yearPanel, BoxLayout.Y_AXIS));
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Please click which year you would like to view");
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton button = new JButton("Return to main menu");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(100, 40));
        button.addActionListener(backAction());
        yearPanel.add(Box.createVerticalStrut(100));
        yearPanel.add(label);
        yearPanel.add(Box.createVerticalStrut(50));
        yearPanel.add(midPanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(button);
        yearPanel.add(bottomPanel);
    }

    // MODIFIES: this
    // EFFECTS: asks user which year they want to view
    public void runGraphClick() {
        createYearPanel();
        runYearPanel();
        ui.switchPanel(yearPanel);
    }

    // MODIFIES: this
    // EFFECTS: collects data and draws graph
    public void runGraph() {
        collectData();
        panelInit();
        ui.switchPanel(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: runs the year panel by adding the years as buttons
    public void runYearPanel() {
        JPanel midPanel = (JPanel) yearPanel.getComponent(3);
        Set<Integer> yearSet = getYearSet();
        if (yearSet.size() == 0) {
            JLabel label = (JLabel) yearPanel.getComponent(1);
            label.setText("There are currently no transactions");
        } else {
            for (Integer i : yearSet) {
                JButton button = new JButton(Integer.toString(i));
                button.addActionListener(yearAction(i));
                button.setPreferredSize(new Dimension(100, 40));
                midPanel.add(button);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: runs through all transactions and collects a set of years they took place in
    public Set<Integer> getYearSet() {
        Set<Integer> temp = new HashSet<>();
        for (Transaction t : ui.getTransactionList()) {
            temp.add(t.getYear());
        }
        return temp;
    }

    // MODIFIES: this
    // EFFECTS: collects graph data and stores it in spending, also determines the graph max height
    // from finding the month with the most spendings
    public void collectData() {
        maxY = 0;
        List<Transaction> tran = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            tran = ui.getTracker().sortMonth(i + 1, yearToGraph);
            int total = findTotal(tran);
            maxY = Math.max(maxY, total);
            spending[i] = total;
        }
        tickSpacing = maxY / TICK_COUNT;
    }

    // EFFECTS: takes in a list of transactions sorted by month and year and adds the total
    // returns the total rounded to the nearest whole number
    public int findTotal(List<Transaction> tran) {
        double total = 0.0;
        for (Transaction t : tran) {
            total += t.getAmount();
        }
        return (int) Math.round(total);
    }

    // MODIFIES: this
    // EFFECTS: draws the y-axis labels and ticks
    public void drawY(Graphics2D g2) {
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (int i = 0; i <= TICK_COUNT; i++) {
            int y = HEIGHT - BOTTOM_MARGIN - (i * TICK_HEIGHT);
            g2.drawLine(LEFT_MARGIN - 5, y, LEFT_MARGIN, y);
            g2.drawString(String.valueOf(i * tickSpacing), LEFT_MARGIN - 40, y + 5);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws the x-axis labels and ticks
    public void drawX(Graphics2D g2) {
        for (int i = 0; i < 12; i++) {
            int x = LEFT_MARGIN + (i * BAR_WIDTH) + BAR_WIDTH / 2;
            g2.drawLine(x, HEIGHT - BOTTOM_MARGIN, x, HEIGHT - BOTTOM_MARGIN + 5); // Tick
            g2.drawString(MONTHS[i], x - 10, HEIGHT - BOTTOM_MARGIN + 20); // Label
        }
    }

    // MODIFIES: this
    // EFFECTS: draws the bars of the bar graph
    public void drawBars(Graphics2D g2) {
        for (int i = 0; i < spending.length; i++) {
            int barHeight = (spending[i] * GRAPH_HEIGHT) / maxY; // Normalize height
            int x = LEFT_MARGIN + (i * BAR_WIDTH) + 10;
            int y = HEIGHT - BOTTOM_MARGIN - barHeight;
            g2.setColor(BAR_COLOR);
            g2.fillRect(x, y, BAR_WIDTH - 20, barHeight);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws the x and y axis labels
    public void drawLabels(Graphics2D g2) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.drawString("Months", WIDTH / 2 + 10, HEIGHT - 20);
        g2.rotate(-Math.PI / 2);
        g2.drawString("Amount Spent ($)", -HEIGHT / 2 - 20, LEFT_MARGIN - 70);
        g2.rotate(Math.PI / 2);
    }

    // MODIFIES: This
    // EFFECTS: draws the graph to the middle panel
    protected void drawGraph(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        String title = "Monthly Spendings for the year " + Integer.toString(yearToGraph);
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (WIDTH - titleWidth) / 2 + 50, TOP_MARGIN / 2);
        g2.drawLine(LEFT_MARGIN, HEIGHT - BOTTOM_MARGIN, LEFT_MARGIN + GRAPH_WIDTH, HEIGHT - BOTTOM_MARGIN); // X-axis
        g2.drawLine(LEFT_MARGIN, HEIGHT - BOTTOM_MARGIN, LEFT_MARGIN, TOP_MARGIN); // Y-axis
        drawY(g2);
        drawX(g2);
        drawBars(g2);
        drawLabels(g2);
    }

    // EFFECTS: creates an ActionListener that takes the user back to main menu when clicked
    public ActionListener backAction() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.backClick();
            }
        };
        return al;
    }

    // EFFECTS: creates an ActionListener to read which year the user clicks and proceeds to switch
    // panels to that corresponding graph
    public ActionListener yearAction(int i) {
        ActionListener al = new ActionListener() {
            // EFFECTS: overrides actionPerformed
            @Override
            public void actionPerformed(ActionEvent e) {
                yearToGraph = i;
                runGraph();
            }
        };
        return al;
    }

    // EFFECTS: checks for button input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            ui.switchPanel(yearPanel);
        }
    }

    // GETTER
    public JPanel getYearPanel() {
        return yearPanel;
    }

}
