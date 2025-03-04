import src.IOHandler;
import src.Database.DatabaseHandler;

import src.Server.DataBaseInterface;

public class App {
    public static void main(String[] args) {
        IOHandler io = new IOHandler();
        DatabaseHandler db = new DatabaseHandler("banking.db");

        db.initialise_database();

        io.set_log_file("app_log.txt");
        io.set_min_log_level(IOHandler.LogLevel.INFO);

        DataBaseInterface db_interface = new DataBaseInterface(db);
    }
}
