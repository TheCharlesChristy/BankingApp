package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

import src.Structs.Accounts;

public class AdminEditAccount extends CommandLineFunctions {
    public AdminEditAccount(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void run(Accounts account) {
        get_prompt_print("AdminEditAccount");
        String choice_str = get_prompt_enter("AdminEditAccountChoice");
        int choice;
        try {
            choice = Integer.parseInt(choice_str);
        } catch (NumberFormatException e) {
            get_prompt_print("InvalidChoice");
            return;
        }
        if (choice < 1 || choice > 2) {
            get_prompt_print("InvalidChoice");
            return;
        }
        switch (choice) {
            case 1:
                // Edit account details such as acc number, associated users and their details
                edit_account_details(account);
                break;
            case 2:
                // Edit account balance
                edit_account_balance(account);
                break;
            default:
                get_prompt_print("InvalidChoice");
        }
    }   

    private void edit_account_details(Accounts account) {
        // Implementation for viewing account details
    }

    private void edit_account_balance(Accounts account) {
        // Implementation for viewing account balance
    }
}
