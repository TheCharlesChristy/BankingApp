package src.Server.CommandLine;

import src.Server.DataBaseInterface;
import src.Database.DatabaseHandler;
import src.IOHandler;
import org.json.simple.JSONObject;

import src.Structs.Accounts;
import src.Structs.Admins;
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

        // Create a new transaction for the deposit
        create_deposit_transaction(account, amount_usd);

        io.println("Deposit successful. New balance: " + account.get_balance());
    }

    public void create_deposit_transaction(Accounts account, float amount_usd) {
        // Create a new transaction for the deposit
        io.println("Creating deposit transaction for account: " + account.get_account_id() + " with amount: " + amount_usd);
        Transactions transaction = new Transactions(account.get_account_id(), amount_usd, "deposit");
        db_interface.transactions_interface.create_transaction(transaction);
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

        // Create a new transaction for the withdrawal
        create_withdraw_transaction(account, amount_usd);

        io.println("Withdrawal successful. New balance: " + account.get_balance());
    }

    public void create_withdraw_transaction(Accounts account, float amount_usd) {
        // Create a new transaction for the withdrawal
        io.println("Creating withdrawal transaction for account: " + account.get_account_id() + " with amount: " + amount_usd);
        Transactions transaction = new Transactions(account.get_account_id(), amount_usd, "withdrawal");
        db_interface.transactions_interface.create_transaction(transaction);
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

        // Create a new transaction for the transfer
        create_withdraw_transaction(from_account, amount);
        create_deposit_transaction(to_account, amount);

        // Update the accounts in the database
        db_interface.account_interface.update_account(from_account);
        db_interface.account_interface.update_account(to_account);

        io.println("Transfer successful. New balance: " + from_account.get_balance());
    }

    public Accounts get_account(int account_num) {
        Accounts account = db_interface.account_interface.get_account(account_num);
        if (account == null) {
            io.println("Account not found.");
            return null;
        }
        return account;
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

    public Accounts register(String username, String password, String email) {
        // Check if the user already exists
        Users user = db_interface.user_interface.get_user(username);
        if (user != null) {
            io.println("User already exists.");
            return null;
        }

        // Create a new user
        user = new Users(username, password, email, generate_pin());
        db_interface.create_user(user);
        user = db_interface.user_interface.get_user(username);
        // Everything is handled in the create_user method

        // Get the account for the user
        Accounts account = db_interface.account_interface.get_account_by_uid(user.get_id());
        if (account == null) {
            // Fatal error: account should have been created in create_user
            io.println("Fatal error: account not found after user creation.");
            return null;
        }

        return account;
    }

    public Accounts login(String username, String password) {
        // Check if the user exists
        Users user = db_interface.user_interface.get_user(username);
        if (user == null) {
            io.println("User not found.");
            return null;
        }

        // Check if the password is correct
        if (!user.verify_password(password)) {
            io.println("Incorrect password.");
            return null;
        }

        // Get the account for the user
        Accounts account = db_interface.account_interface.get_account_by_uid(user.get_id());
        if (account == null) {
            io.println("Account not found.");
            return null;
        }

        return account;
    }

    public Users login_user(String username, String password) {
        // Check if the user exists
        Users user = db_interface.user_interface.get_user(username);
        return user;
        // if (user == null) {
        //     io.println("User not found.");
        //     return null;
        // }

        // // Check if the password is correct
        // if (!user.verify_password(password)) {
        //     io.println("Incorrect password.");
        //     return null;
        // }

        // return user;
    }

    public boolean is_user_admin(Users user) {
        // Check if the user is an admin
        List<Admins> admins = db_interface.admins_interface.get_all_admins(db_interface.user_interface);
        
        for (Admins admin : admins) {
            if (admin.username.equals(user.get_username())) {
                return true;
            }
        }
        return false;
    }

    public UserInstance get_user_instance(Accounts account) {
        // Get the user instance for the account
        Users user = db_interface.user_interface.get_user(account.user_id);

        if (user == null) {
            io.println("User not found.");
            return null;
        }

        UserInstance user_instance = new UserInstance(account, user, is_user_admin(user));
        return user_instance;
    }

    public UserInstance get_user_instance(Users user) {
        // Get the user instance for the user
        Accounts account = db_interface.account_interface.get_account_by_uid(user.get_id());

        UserInstance user_instance = new UserInstance(account, user, is_user_admin(user));
        return user_instance;
    }
}
