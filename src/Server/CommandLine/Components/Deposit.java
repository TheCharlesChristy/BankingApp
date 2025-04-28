package src.Server.CommandLine.Components;

import java.util.Optional;

import src.Server.CommandLine.CommandLineFunctions;

import src.Server.DataBaseInterface;

import src.Structs.Users;
import src.Structs.Accounts;
import src.Structs.Currency;

public class Deposit extends CommandLineFunctions{
    Users user;
    Accounts account;
    public Deposit(DataBaseInterface db_interface, Users user, Accounts account) {
        super(db_interface);
        this.user = user;
        this.account = account;
    }
    
    public Deposit(DataBaseInterface db_interface, Users user) {
        super(db_interface);
        this.user = user;
    }

    public Deposit(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void set_user(Users user) {
        this.user = user;
    }

    public void set_account(Accounts account) {
        this.account = account;
    }

    public float convert_currency(float amount, Currency currency) {
        switch (currency) {
            case Currency.USD:
                return amount;

            case Currency.EUR:
                return amount / 0.85f;

            case Currency.JPY:
                return amount / 110.0f;

            case Currency.GBP:
                return amount / 0.72f;

            default:
                return amount;
        }
    }

    private Currency get_currency(int curr_type) {
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

    public void run() {
        prompt_deposit();
    }

    public void clear() {
        user = null;
        account = null;
    }

    private boolean verify_input(String input) {
        try {
            Integer value = Integer.parseInt(input);

            return value > 0 && value < 6;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void prompt_deposit() {
        get_prompt_print("Deposit");
        get_prompt_print("DepositType");
        int incorrect_attempts = 0;
        while (incorrect_attempts < 3) {
            String DepositType = get_prompt_enter("DepositTypeChoice");
            if (!verify_input(DepositType)) {
                io.println("Invalid input");
                incorrect_attempts++;
                continue;
            }else{
                String deposit_amount_str = get_prompt_enter("DepositAmount");
                float deposit_amount = 0;
                try {
                    deposit_amount = Float.parseFloat(deposit_amount_str);
                } catch (NumberFormatException e) {
                    io.println("Invalid amount. Please enter a valid number.");
                    continue;
                }
                Currency currency = get_currency(Integer.parseInt(DepositType));
                float amount_in_usd = convert_currency(deposit_amount, currency);

                account.deposit(amount_in_usd);
                db_interface.account_interface.update_account(account);

                io.println("Deposit successful. New balance: " + account.balance);
                break;
            }
        }
    }
}
