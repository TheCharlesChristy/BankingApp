package src.Server.CommandLine.Components;

import src.Server.CommandLine.CommandLineFunctions;

import src.Server.DataBaseInterface;

import src.Structs.Users;
import src.Structs.Accounts;
import src.Structs.Currency;


public class ViewBalance extends CommandLineFunctions{
    Users user;
    
    public ViewBalance(DataBaseInterface db_interface, Users user) {
        super(db_interface);
        this.user = user;
    }

    public ViewBalance(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void set_user(Users user) {
        this.user = user;
    }

    public void run(Users user) {
        prompt_view_balance(user);
    }

    public void clear() {
        user = null;
    }

    public float convert_currency(float amount, Currency currency) {
        float converted_amount = amount;
        io.debug("Converting currency: " + amount + " " + currency.name());
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

    public Currency convert_choice_to_currency(String choice) {
        switch (choice) {
            case "1":
                return Currency.USD;

            case "2":
                return Currency.EUR;

            case "3":
                return Currency.JPY;

            case "4":
                return Currency.GBP;

            default:
                return Currency.USD;
        }
    }

    private boolean is_choice_valid(String choice) {
        return choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4");
    }

    public void prompt_view_balance(Users user) {
        int uid = db_interface.user_interface.get_userid(user.username);
        Accounts account = db_interface.account_interface.get_account_by_uid(uid);
        Currency currency = Currency.USD;
        String choice = "";
        while (true) {
            // Print the balance in the selected currency
            get_prompt_print("ViewBalance");
            io.println("Balance: " + convert_currency(account.get_balance(), currency) + " " + currency.name());

            // Print and prompt the options for currency selection
            get_prompt_print("ViewBalanceOptions");
            choice = get_prompt_enter("ViewBalanceChoice");

            // Check if the choice is valid and set the currency accordingly
            if (is_choice_valid(choice)) {
                currency = convert_choice_to_currency(choice);
            }else if (choice.equals("5")) {
                break;
            } else {
                io.println("Invalid choice");
            }
        }
    }
}
