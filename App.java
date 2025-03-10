import src.IOHandler;
import src.Database.DatabaseHandler;

import src.Server.DataBaseInterface;

import src.Structs.Accounts;
import src.Structs.Users;

public class App {
    public static void main(String[] args) {
        IOHandler io = new IOHandler();
        DatabaseHandler db = new DatabaseHandler("banking.db");

        db.create_database();
        db.initialise_database();

        io.set_log_file("app_log.txt");
        io.set_min_log_level(IOHandler.LogLevel.INFO);

        DataBaseInterface db_interface = new DataBaseInterface(db);

        Accounts account = new Accounts(1, 1, 10.0f, 0.1f);
        db_interface.account_interface.create_account(account);
        Accounts test_acc = db_interface.account_interface.get_account(1);
    }
}
