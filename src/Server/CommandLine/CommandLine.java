package src.Server.CommandLine;

import src.Server.DataBaseInterface;
import src.Database.DatabaseHandler;
import src.IOHandler;
import org.json.simple.JSONObject;

import src.Structs.Accounts;
import src.Structs.UserInstance;
import src.Structs.Users;

import java.io.IOException;

public class CommandLine {
    private DataBaseInterface db_interface;
    private IOHandler io;
    private UserInstance user_instance;

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
            json = io.read_json("src/Assets/prompts.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) json.get(key);
    }

    private void get_prompt_print(String key) {
        String msg = get_prompt(key);
        io.println(msg);
    }

    private String get_prompt_enter(String key) {
        String msg = get_prompt(key);
        return io.prompt(msg);
    }

    private String get_prompt_enter_pass(String key) {
        String msg = get_prompt(key);
        return io.prompt_password(msg);
    }

    public void prompt_welcome() {
        get_prompt_print("Welcome");
    }

    public void prompt_main_menu() {
        get_prompt_print("MainMenu");
    }

    public void prompt_login() {
        get_prompt_print("Login");
    }

    public void prompt_register() {
        get_prompt_print("Register");
        String username = get_prompt_enter("RegisterUsername");
        
        // Check if user exists return to main menu if they do
        if (db_interface.user_interface.check_exists(username)) {
            io.println("User already exists");
            return;
        }

        // Create user if they do not exist
        String password = get_prompt_enter_pass("RegisterPassword");
        String confirmed_password = get_prompt_enter_pass("RegisterPasswordConfirm");
        if (password.equals(confirmed_password)) {
            io.println("User created successfully");
            String email = get_prompt_enter("RegisterEmail");
            int pin = generate_pin();
            io.println("Your pin is: " + pin);
            db_interface.user_interface.create_user(new Users(username, password, email, pin));
            return;
        }
        io.println("Passwords do not match");
        return;
    }

    private int generate_pin() {
        return (int) (Math.random() * 10000);
    }

    public void run() {
        prompt_welcome();
        while (true) {
            prompt_main_menu();
            int choice  = io.prompt_int_no_loop("Enter your choice: ");
            switch (choice) {
                case 1:
                    prompt_login();
                    break;
                case 2:
                    prompt_register();
                    break;
                case 3:
                    io.println("Thank you for your time");
                    return;
                default:
                    io.println("Invalid choice");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine("banking.db");
        cmd.prompt_welcome();
    }
}
