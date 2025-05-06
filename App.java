import java.util.List;

import src.IOHandler;
import src.Database.DatabaseHandler;

import src.Server.DataBaseInterface;

import src.Server.CommandLine.CommandLineInterface;
import src.Structs.Accounts;
import src.Structs.Admins;
import src.Structs.Users;

public class App {
    public IOHandler io;
    public DatabaseHandler db;
    public DataBaseInterface db_interface;
    public CommandLineInterface cmd;

    private void create_io() {
        io = new IOHandler();
        io.set_log_file("app_log.txt");
        io.set_min_log_level(IOHandler.LogLevel.ERROR);
    }

    private void create_cmd() {
        cmd = new CommandLineInterface(db_interface);
    }

    private void create_db() {
        db = new DatabaseHandler("banking.db");
        db.create_database();
        db.initialise_database();
        db_interface = new DataBaseInterface(db);
    }

    public void init() {
        create_io();
        create_db();
        create_cmd();
    }

    public static void main(String[] args) {
        App app = new App();
        app.init();

        app.cmd.run();
    }
}
