package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;


public class AdminAccountManager extends CommandLineFunctions {
    
    public AdminAccountManager(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public int run() {
        return prompt_account_manager();
    }

    private boolean verify_input(String input) {
        if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")){
            return true;
        }
        return false;
    }

    public int prompt_account_manager() {
        get_prompt_print("AdminAccountManager");
        int incorrect_attempts = 0;
        while (incorrect_attempts < 3) {
            String choice = get_prompt_enter("AdminAccountManagerChoice");
            if (!verify_input(choice)) {
                io.println("Invalid input");
                incorrect_attempts++;
                continue;
            }else{
                return Integer.parseInt(choice);
            }
        }
        return 4; // logout
    }
}
