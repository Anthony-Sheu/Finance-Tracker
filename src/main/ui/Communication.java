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

    // EFFECTS: show all transactions
    void showAllClick();

    // EFFECTS: switch to bank menu
    void bankClick();

    // EFFECTS: switches to add bank panel
    void addBankClick();

    // EFFECTS: switches to viewing bank balances
    void bankBalanceClick();

    // EFFECTS: switches to showing transactions in time range
    void showInDateClick();
}
