package src.Server.CommandLine;

import src.Server.DataBaseInterface;
import src.Database.DatabaseHandler;
import src.IOHandler;
import org.json.simple.JSONObject;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    private String get_prompt(String key) {
        JSONObject json = null;
        try {
            json = io.read_json("src/Server/CommandLine/prompts.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) json.get(key);
    }

    public void prompt_welcome() {
        String msg = get_prompt("Welcome");
        io.println(msg);
    }

    public void prompt_main() {
        
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine("banking.db");
        cmd.prompt_welcome();
    }
}
