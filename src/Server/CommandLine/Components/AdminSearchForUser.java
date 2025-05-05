package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

import src.Structs.Accounts;

public class AdminSearchForUser extends CommandLineFunctions {
    public AdminSearchForUser(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public Accounts run() {
        return prompt_search_for_user();
    }

    public Accounts prompt_search_for_user() {
        get_prompt_print("AdminSearchForUser");
        int incorrect_attempts = 0;
        while (incorrect_attempts < 3) {
            String username = get_prompt_enter("AdminSearchForUserUsername");
            Accounts account = get_account_by_username(username);
            if (account != null) {
                return account;
            } else {
                String choice = get_prompt_enter("AdminSearchForUserNotFound");
                if (choice.equals("n")) {
                    return null;
                }
                incorrect_attempts++;
            }
        }
        get_prompt_print("AdminSearchForUserMaxAttempts");
        return null;
    }
    
}
