package src.Server.CommandLine.Components;

import java.util.Optional;

import src.Server.CommandLine.CommandLineFunctions;

import src.Server.DataBaseInterface;

import src.Structs.Users;
import src.Structs.Accounts;
import src.Structs.Currency;

public class FundsManagerBase extends CommandLineFunctions{
    Users user;
    Accounts account;
    public FundsManagerBase(DataBaseInterface db_interface, Users user, Accounts account) {
        super(db_interface);
        this.user = user;
        this.account = account;
    }
    
    public FundsManagerBase(DataBaseInterface db_interface, Users user) {
        super(db_interface);
        this.user = user;
    }

    public FundsManagerBase(DataBaseInterface db_interface) {
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

    protected Currency get_currency(int curr_type) {
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
        // This method should be overridden in subclasses
        throw new UnsupportedOperationException("This method should be overridden in subclasses.");
    }

    public void clear() {
        user = null;
        account = null;
    }

    protected boolean verify_input(String input) {
        try {
            Integer value = Integer.parseInt(input);

            return value > 0 && value < 6;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
