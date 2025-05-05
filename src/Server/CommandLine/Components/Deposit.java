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

            // Check if the input is valid
            if (!verify_input(DepositType)) {
                io.println("Invalid input");
                incorrect_attempts++;
                continue;
            }else{
                int DepositTypeInt = Integer.parseInt(DepositType);
                // Exit if the user enters 5
                if (DepositTypeInt == 5) {
                    io.println("Exiting deposit prompt.");
                    return;
                }

                // Get the deposit amount from the user
                String deposit_amount_str = get_prompt_enter("DepositAmount");
                float deposit_amount = 0;
                try {
                    deposit_amount = Float.parseFloat(deposit_amount_str);
                } catch (NumberFormatException e) {
                    io.println("Invalid amount. Please enter a valid number.");
                    continue;
                }

                // Get the currency type
                Currency currency = get_currency(DepositTypeInt);
                float amount_in_usd = convert_currency(deposit_amount, currency);

                // Deposit the amount into the account
                account.deposit(amount_in_usd);
                db_interface.account_interface.update_account(account);

                String new_balance_text = convert_currency(account.get_balance(), currency) + currency.name();

                io.println("Deposit successful. New balance: " + new_balance_text);
                break;
            }
        }
    }
}
