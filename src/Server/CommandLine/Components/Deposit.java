package src.Server.CommandLine.Components;



import src.Server.DataBaseInterface;

import src.Structs.Users;
import src.Structs.Accounts;
import src.Structs.Currency;

public class Deposit extends FundsManagerBase{
    public Deposit(DataBaseInterface db_interface, Users user, Accounts account) {
        super(db_interface, user, account);
    }
    
    public Deposit(DataBaseInterface db_interface, Users user) {
        super(db_interface, user);
    }

    public Deposit(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void run() {
        prompt_deposit();
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
                float amount_in_usd = convert_from_currency(deposit_amount, currency);

                // Deposit the amount into the account
                deposit(account, amount_in_usd);

                String new_balance_text = convert_from_currency(account.get_balance(), currency) + currency.name();

                io.println("Deposit successful. New balance: " + new_balance_text);
                break;
            }
        }
    }
}
