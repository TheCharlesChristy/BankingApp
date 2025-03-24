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

    public boolean verify_password_req(String password) {
        // Password must be longer than 8 chars and contain a capital letter and a number
        if (password.length() < 8) {
            io.println("Password must be at least 8 characters long");
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            io.println("Password must contain at least one capital letter");
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            io.println("Password must contain at least one lowercase letter");
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            io.println("Password must contain at least one number");
            return false;
        }
        return true;
    }

    public boolean verify_email(String email) {
        if (!email.contains("@")) {
            io.println("Invalid email");
            return false;
        }
        if (!email.contains(".")) {
            io.println("Invalid email");
            return false;
        }
        // Split the email at the last '.' and check if the last part is less than 2 characters
        String[] split_email = email.split("\\.");
        if (split_email[split_email.length - 1].length() < 2) {
            io.println("Invalid email");
            return false;
        }

        return true;
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
        int counter = 0;
        String password = "";
        get_prompt_print("RegisterPasswordReq");
        while (counter < 3) {
            password = get_prompt_enter_pass("RegisterPassword");
            if (verify_password_req(password)) {
                break;
            }
            counter++;
        }
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
