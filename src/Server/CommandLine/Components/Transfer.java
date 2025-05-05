package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

import src.Structs.Accounts;
import src.Structs.Currency;

public class Transfer extends CommandLineFunctions {

    public Transfer(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void run(Accounts from_account) {
        get_prompt_print("Transfer");
        
        // Prompt for to account
        Accounts to_account = prompt_find_to_account();
        if (to_account == null || to_account.get_account_id() == from_account.get_account_id()) {
            return;
        }

        // Prompt for amount to transfer
        float amount = prompt_transfer_amount(from_account);
        if (amount <= 0) {
            io.println("Invalid amount");
            return;
        }

        transfer(from_account, to_account, amount);
    }

    private Accounts prompt_find_to_account() {
        String account_id_str = get_prompt_enter("TransferToAccount");

        // Parse account_id to int
        int account_id = 0;
        try {
            account_id = Integer.parseInt(account_id_str);
        } catch (NumberFormatException e) {
            io.println("Invalid account id");
            return null;
        }

        // Check if account exists return to main menu if they do
        if (!db_interface.account_interface.check_exists(account_id)) {
            io.println("Account does not exist");
            return null;
        }

        return db_interface.account_interface.get_account(account_id);
    }

    private float prompt_transfer_amount(Accounts from_account) {
        // Prompt the user for the currency and amount to transfer
        get_prompt_print("TransferCurrency");
        String currency_string = get_prompt_enter("TransferCurrencyChoice");
        
        // Convert to int and check if in valid range
        int currency_int = 0;
        try {
            currency_int = Integer.parseInt(currency_string);
        } catch (NumberFormatException e) {
            io.println("Invalid currency choice");
            return -1;
        }
        if (currency_int < 0 || currency_int > 5) {
            io.println("Invalid currency choice");
            return -1;
        }

        // Get as currency type
        Currency currency = get_currency(currency_int);

        // Prompt for amount to transfer
        String amount_string = get_prompt_enter("TransferAmount");
        // Parse amount to float
        float amount = 0;
        try {
            amount = Float.parseFloat(amount_string);
        } catch (NumberFormatException e) {
            io.println("Invalid amount");
            return -1;
        }

        // Check if amount is valid
        if (amount <= 0 || amount > convert_to_currency(from_account.get_balance(), currency)) {
            io.println("Invalid amount");
            return -1;
        }

        // Return amount in USD
        return convert_from_currency(amount, currency);
    }
}
