package src.Server.CommandLine.Components;

import src.Server.CommandLine.CommandLineFunctions;

import src.Server.DataBaseInterface;

import src.Structs.Users;

public class Register extends CommandLineFunctions {

    public Register(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void run() {
        prompt_register();
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
            db_interface.create_user(new Users(username, password, email, pin));
            return;
        }
        io.println("Passwords do not match");
        return;
    }
}
