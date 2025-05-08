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
        get_prompt_print("AdminSearchForAccount");
        int incorrect_attempts = 0;
        while (incorrect_attempts < 3) {
            String acc_num_str = get_prompt_enter("AdminSearchForAccountSearch");
            int account_num;
            try {
                account_num = Integer.parseInt(acc_num_str);
            } catch (NumberFormatException e) {
                get_prompt_print("AdminSearchForAccountInvalidInput");
                incorrect_attempts++;
                continue;
            }
            if (account_num < 0) {
                get_prompt_print("AdminSearchForAccountInvalidInput");
                incorrect_attempts++;
                continue;
            }
            Accounts account = get_account(account_num);
            if (account != null) {
                return account;
            } else {
                String choice = get_prompt_enter("AdminSearchForAccountNotFound");
                if (choice.equals("n")) {
                    return null;
                }
                incorrect_attempts++;
            }
        }
        get_prompt_print("AdminSearchForAccountMaxAttempts");
        return null;
    }
    
}
