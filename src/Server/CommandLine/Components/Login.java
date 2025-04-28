package src.Server.CommandLine.Components;

import src.Server.CommandLine.CommandLineFunctions;

import src.Server.DataBaseInterface;

import src.Structs.Users;

public class Login extends CommandLineFunctions {

    public Login(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public Users run() {
        return prompt_login();
    }

    public Users prompt_login() {
        get_prompt_print("Login");
        String username = get_prompt_enter("LoginUsername");

        // Check if user exists return to main menu if they do
        if (!db_interface.user_interface.check_exists(username)) {
            io.println("User does not exist");
            return null;
        }

        while (true) {
            String password = get_prompt_enter_pass("LoginPassword");
            // Check if password is correct
            if (db_interface.user_interface.check_password(username, password)) {
                io.println("Login successful");
                return db_interface.user_interface.get_user(username);
            }

            io.println("Password is incorrect");

            String try_again = "";
            do {
                try_again = get_prompt_enter("LoginTryAgain");
            } while (!try_again.equals("y") && !try_again.equals("n"));

            if (try_again.equals("n")) {
                return null;
            }
        }
    }
    
}
