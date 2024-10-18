package persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import model.Banks;
import model.Account;

import java.util.*;

public class BankWriter extends JsonWriter {

    // EFFETCS: constructs a jsonwriter for bank
    public BankWriter(String destination) {
        super(destination);
    }

    // EFFECTS: returns a JSONObject of the data that
    // is going to be written
    public JSONObject toJson(Banks bank) {
        JSONObject json = new JSONObject();
        json.put("Accounts", accountsToJson(bank.getBank()));
        return json;
    }

    // EFFECTS: returns accounts in JSON array
    public JSONArray accountsToJson(List<Account> accounts) {
        JSONArray jsonArray = new JSONArray();
        for (Account a : accounts) {
            jsonArray.put(accountToJson(a));
        }
        return jsonArray;
    }

    // EFFECTS: takes in an account and converts it into 
    // a JSON object
    public JSONObject accountToJson(Account account) {
        JSONObject json = new JSONObject();
        json.put("Bank", account.getBank());
        json.put("Chequeing", account.getChequeing());
        json.put("Savings", account.getSavings());
        json.put("Credit", account.getCredit());
        json.put("Credit Limit", account.getCreditLimit());
        json.put("Chequeing Overdraft", account.checkOverdraftChequeing());
        json.put("Savings Overdraft", account.checkOverdraftSavings());
        json.put("Credit Overusage", account.checkOverLimit());
        return json;
    }

}
