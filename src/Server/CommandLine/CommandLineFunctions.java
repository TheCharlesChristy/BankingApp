package src.Server.CommandLine;

import src.Server.DataBaseInterface;
import src.Database.DatabaseHandler;
import src.IOHandler;
import org.json.simple.JSONObject;

import src.Structs.Accounts;
import src.Structs.UserInstance;
import src.Structs.Users;

import java.io.IOException;

public class CommandLineFunctions {
    protected DataBaseInterface db_interface;
    protected IOHandler io;

    public CommandLineFunctions(DataBaseInterface db_interface) {
        this.db_interface = db_interface;
        this.io = new IOHandler();
    }

    public CommandLineFunctions(String db_name) {
        this.db_interface = new DataBaseInterface(db_name);
        this.io = new IOHandler();
    }

    public CommandLineFunctions(DatabaseHandler db_handler) {
        this.db_interface = new DataBaseInterface(db_handler);
        this.io = new IOHandler();
    }

    protected String get_prompt(String key) {
        JSONObject json = null;
        try {
            json = io.read_json("src/Assets/prompts.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) json.get(key);
    }

    protected void get_prompt_print(String key) {
        String msg = get_prompt(key);
        io.println(msg);
    }

    protected String get_prompt_enter(String key) {
        String msg = get_prompt(key);
        return io.prompt(msg);
    }

    protected String get_prompt_enter_pass(String key) {
        String msg = get_prompt(key);
        return io.prompt(msg);
    }

    protected int generate_pin() {
        return (int) (Math.random() * 10000);
    }
}
