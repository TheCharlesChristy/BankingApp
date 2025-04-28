package src.Server;

import src.Database.DatabaseHandler;

import src.Server.Interfaces.AccountInterface;
import src.Server.Interfaces.UserInterface;
import src.Server.Interfaces.AdminsInterface;
import src.Server.Interfaces.TransactionsInterface;

import src.Structs.Users;
import src.Structs.Accounts;

public class DataBaseInterface {
    DatabaseHandler db;
    public AccountInterface account_interface;
    public UserInterface user_interface;
    public AdminsInterface admins_interface;
    public TransactionsInterface transactions_interface;

    public DataBaseInterface() {
        this.db = new DatabaseHandler("banking.db");
        this.db.initialise_database();
        create_interfaces();
    }

    public DataBaseInterface(String db_name) {
        this.db = new DatabaseHandler(db_name);
        this.db.initialise_database();
        create_interfaces();
    }

    public DataBaseInterface(DatabaseHandler db) {
        this.db = db;
        create_interfaces();
    }

    private void create_interfaces() {
        this.account_interface = new AccountInterface(this.db);
        this.user_interface = new UserInterface(this.db);
        this.admins_interface = new AdminsInterface(this.db);
        this.transactions_interface = new TransactionsInterface(this.db);
    }

    public boolean login(String username, String password) {
        return user_interface.login(username, password);
    }

    public boolean register(String username, String password, String email, int pin) {
        return user_interface.register(username, password, email, pin);
    }

    public void create_user(Users usr) {
        Accounts acc = new Accounts(usr.get_id());
        user_interface.create_user(usr);
        account_interface.create_account(acc);
    }
}
