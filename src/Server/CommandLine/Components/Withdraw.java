package src.Server.CommandLine.Components;



import src.Server.DataBaseInterface;

import src.Structs.Users;
import src.Structs.Accounts;
import src.Structs.Currency;

public class Withdraw extends FundsManagerBase{
    public Withdraw(DataBaseInterface db_interface, Users user, Accounts account) {
        super(db_interface, user, account);
    }
    
    public Withdraw(DataBaseInterface db_interface, Users user) {
        super(db_interface, user);
    }

    public Withdraw(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void run() {
        prompt_withdraw();
    }

    public void prompt_withdraw() {
        get_prompt_print("Withdraw");
        get_prompt_print("WithdrawType");
        int incorrect_attempts = 0;
        while (incorrect_attempts < 3) {
            String WithdrawType = get_prompt_enter("WithdrawTypeChoice");

            // Check if the input is valid
            if (!verify_input(WithdrawType)) {
                io.println("Invalid input");
                incorrect_attempts++;
                continue;
            }else{
                int WithdrawTypeInt = Integer.parseInt(WithdrawType);
                // Exit if the user enters 5
                if (WithdrawTypeInt == 5) {
                    io.println("Exiting withdraw prompt.");
                    return;
                }

                // Print the users balance in the selected currency
                Currency currency = get_currency(WithdrawTypeInt);
                String balance_text = convert_to_currency(account.get_balance(), currency) + currency.name();
                io.println("Your balance is: " + balance_text);


                // Get the withdraw amount from the user
                String withdraw_amount_str = get_prompt_enter("WithdrawAmount");
                float withdraw_amount = 0;
                try {
                    withdraw_amount = Float.parseFloat(withdraw_amount_str);
                    float current_converted_balance = convert_to_currency(account.get_balance(), currency);
                    if (withdraw_amount > current_converted_balance) {
                        io.println("Insufficient funds. Please enter a valid amount.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    io.println("Invalid amount. Please enter a valid number.");
                    continue;
                }

                // Get the converted amount in USD
                float amount_in_usd = convert_from_currency(withdraw_amount, currency);

                io.debug("Converted amount in USD: " + amount_in_usd);

                // Withdraw the amount into the account
                withdraw(account, amount_in_usd);

                String new_balance_text = convert_to_currency(account.get_balance(), currency) + currency.name();

                io.println("Withdraw successful. New balance: " + new_balance_text);
                break;
            }
        }
    }
}
