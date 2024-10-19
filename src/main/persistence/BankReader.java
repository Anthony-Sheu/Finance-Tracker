package persistence;

import model.Account;
import model.Banks;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.*;
import java.io.IOException;

// Represents a reader that reads bank from JSON data stored in file
public class BankReader extends JsonReader {

    // EFFECTS: construcs reader to read Bank JSON file
    public BankReader(String source) {
        super(source);
    }

    // EFFECTS: converts JSONObject to Bank
    public Banks toBanks(JSONObject json) {
        Banks bank = new Banks();
        JSONArray jsonArray = json.getJSONArray("Accounts");
        for (Object o : jsonArray) {
            JSONObject nextJson = (JSONObject) o;
            toAccount(bank, nextJson);
        }
        return bank;
    }

    // MODIFIES: this
    // EFFECTS: converts JSONObject to Account and adds it to bank
    public void toAccount(Banks bank, JSONObject json) {
        String name = json.getString("Bank");
        double cheq = json.getDouble("Chequeing");
        double save = json.getDouble("Savings");
        double cred = json.getDouble("Credit");
        double credLim = json.getDouble("Credit Limit");
        boolean cheqOver = json.getBoolean("Chequeing Overdraft");
        boolean saveOver = json.getBoolean("Savings Overdraft");
        boolean credOver = json.getBoolean("Credit Overusage");
        Account account = new Account(cheq, save, cred, name, credLim);
        account.loadAccount(cheqOver, saveOver, credOver);
        bank.newAccount(account);
    }

}
