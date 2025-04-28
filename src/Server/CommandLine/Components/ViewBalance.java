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

    public float convert_currency(float amount, Currency currency) {
        switch (currency) {
            case Currency.USD:
                return amount;

            case Currency.EUR:
                return amount * 0.85f;

            case Currency.JPY:
                return amount * 110.0f;

            case Currency.GBP:
                return amount * 0.72f;

            default:
                return amount;
        }
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
            get_prompt_print("ViewBalance");
            io.println("Balance: " + convert_currency(account.balance, currency) + " " + currency.name());
            get_prompt_print("ViewBalanceOptions");
            choice = get_prompt_enter("ViewBalanceChoice");

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
