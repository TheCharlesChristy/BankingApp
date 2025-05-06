package src.Server.CommandLine;

import src.Server.DataBaseInterface;
import src.Database.DatabaseHandler;
import src.IOHandler;
import org.json.simple.JSONObject;

import src.Structs.Accounts;
import src.Structs.Currency;
import src.Structs.Transactions;
import src.Structs.UserInstance;
import src.Structs.Users;

import java.io.IOException;
import java.util.List;

public class CommandLineFunctions {
    protected DataBaseInterface db_interface;
    protected IOHandler io;

    public CommandLineFunctions(DataBaseInterface db_interface) {
        this.db_interface = db_interface;
        this.io = new IOHandler();
    }

    public CommandLineFunctions(String db_name) {
        this.db_interface = new DataBaseInterface(db_name);
        this.io = new IOHandler();
    }

    public CommandLineFunctions(DatabaseHandler db_handler) {
        this.db_interface = new DataBaseInterface(db_handler);
        this.io = new IOHandler();
    }

    protected String get_prompt(String key) {
        JSONObject json = null;
        try {
            json = io.read_json("src/Assets/prompts.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) json.get(key);
    }

    protected void get_prompt_print(String key) {
        String msg = get_prompt(key);
        io.println(msg);
    }

    protected String get_prompt_enter(String key) {
        String msg = get_prompt(key);
        return io.prompt(msg);
    }

    protected String get_prompt_enter_pass(String key) {
        String msg = get_prompt(key);
        return io.prompt(msg);
    }

    protected int generate_pin() {
        return (int) (Math.random() * 10000);
    }

    public float convert_from_currency(float amount, Currency currency) {
        float converted_amount;
        switch (currency) {
            case Currency.USD:
                converted_amount = amount;
                break;

            case Currency.EUR:
                converted_amount =  amount / 0.85f;
                break;

            case Currency.JPY:
                converted_amount =  amount / 110.0f;
                break;

            case Currency.GBP:
                converted_amount = amount / 0.72f;
                break;

            default:
                converted_amount =  amount;
                break;
        }

        // Truncate the converted amount to 2 decimal places
        return (int)(converted_amount * 100) / 100.0f;
    }

    public float convert_to_currency(float amount, Currency currency) {
        float converted_amount = amount;
        switch (currency) {
            case Currency.USD:
                converted_amount = amount;
                break;

            case Currency.EUR:
                converted_amount = amount * 0.85f;
                break;

            case Currency.JPY:
                converted_amount = amount * 110.0f;
                break;

            case Currency.GBP:
                converted_amount = amount * 0.72f;
                break;
        }

        // Truncate the converted amount to 2 decimal places
        return (int)(converted_amount * 100) / 100.0f;
    }

    public Currency get_currency(int curr_type) {
        switch (curr_type) {
            case 1:
                return Currency.USD;

            case 2:
                return Currency.EUR;

            case 3:
                return Currency.JPY;

            case 4:
                return Currency.GBP;

        }
        return Currency.USD;
    }

    public float get_balance(Accounts account, Currency currency) {
        // Convert the balance to the specified currency
        return convert_to_currency(account.get_balance(), currency);
    }

    public void deposit(Accounts account, float amount_usd) {
        // Check if the deposit is valid
        if (amount_usd <= 0) {
            io.println("Invalid deposit amount.");
            return;
        }

        // Perform the deposit
        account.deposit(amount_usd);

        // Update the account in the database
        db_interface.account_interface.update_account(account);

        io.println("Deposit successful. New balance: " + account.get_balance());
    }

    public void withdraw(Accounts account, float amount_usd) {
        // Check if the withdrawal is valid
        if (amount_usd <= 0 || amount_usd > account.get_balance()) {
            io.println("Invalid withdrawal amount.");
            return;
        }

        // Perform the withdrawal
        account.withdraw(amount_usd);

        // Update the account in the database
        db_interface.account_interface.update_account(account);

        io.println("Withdrawal successful. New balance: " + account.get_balance());
    }

    public void transfer(Accounts from_account, Accounts to_account, float amount) {
        // Check if the transfer is valid
        if (from_account.get_balance() < amount) {
            io.println("Insufficient funds for transfer.");
            return;
        }

        // Perform the transfer
        from_account.withdraw(amount);
        to_account.deposit(amount);

        // Update the accounts in the database
        db_interface.account_interface.update_account(from_account);
        db_interface.account_interface.update_account(to_account);

        io.println("Transfer successful. New balance: " + from_account.get_balance());
    }

    public Accounts get_account_by_username(String username) {
        Users user = db_interface.user_interface.get_user(username);
        if (user == null) {
            io.println("User not found.");
            return null;
        }

        Accounts account = db_interface.account_interface.get_account_by_uid(user.get_id());
        return account;
    }

    public List<Transactions> get_account_transactions(Accounts account) {
        List<Transactions> transactions = db_interface.transactions_interface.get_transactions(account.get_account_id());
        return transactions;
    }
}
