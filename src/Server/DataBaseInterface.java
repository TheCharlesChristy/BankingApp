package src.Server;

import src.Database.DatabaseHandler;

public class DataBaseInterface {
    DatabaseHandler db;

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
        // Create interfaces here
    }
}
