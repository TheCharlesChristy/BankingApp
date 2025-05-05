package src.Server.CommandLine.Components;


import src.Server.CommandLine.CommandLineFunctions;

import src.Server.DataBaseInterface;

import src.Structs.Users;
import src.Structs.Accounts;
import src.Structs.Currency;

public class FundsManagerBase extends CommandLineFunctions{
    public Users user;
    public Accounts account;
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
