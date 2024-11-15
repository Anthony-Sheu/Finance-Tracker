package ui;

// Represents a communication interface for panel classes to communicate 
// with the main frame
public interface Communication {

    // EFFECTS: switch to transaction menu
    void transactionClick();

    // EFFECTS: quit program
    void quitClick();

    // EFFECTS: return to main panel
    void backClick();

    // EFECTS: add transaction
    void addTransactionClick();
}