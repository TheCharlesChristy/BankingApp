package src.Server.CommandLine;

import src.Server.DataBaseInterface;
import src.Database.DatabaseHandler;
import src.IOHandler;

public class CommandLine {
    private DataBaseInterface db_interface;
    private IOHandler io;

    public CommandLine(DataBaseInterface db_interface) {
        this.db_interface = db_interface;
        this.io = new IOHandler();
    }

    public CommandLine(String db_name) {
        this.db_interface = new DataBaseInterface(db_name);
        this.io = new IOHandler();
    }

    public CommandLine(DatabaseHandler db_handler) {
        this.db_interface = new DataBaseInterface(db_handler);
        this.io = new IOHandler();
    }

    public void prompt_welcome() {
        io.println("Welcome to the banking system");
    }

    public void prompt_main() {
        
    }
}
