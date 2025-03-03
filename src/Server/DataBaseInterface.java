package src.Server;

import src.Database.DatabaseHandler;

import src.Structs.User;

import src.Server.Interfaces.AccountInterface;

public class DataBaseInterface {
    DatabaseHandler db;
    public AccountInterface account_interface;

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
        account_interface = new AccountInterface(this.db);
    }
}
