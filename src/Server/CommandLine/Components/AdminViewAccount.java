package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

import src.Structs.Accounts;

public class AdminViewAccount extends CommandLineFunctions {
    public AdminViewAccount(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void run(Accounts account) {
        int invalidChoices = 0;
        final int max_invalid_attempts = 3;
        boolean running = true;
        
        while (running && invalidChoices < max_invalid_attempts) {
            get_prompt_print("AdminViewAccount");
            String choice_str = get_prompt_enter("AdminViewAccountChoice");
            int choice;
            try {
                choice = Integer.parseInt(choice_str);
            } catch (NumberFormatException e) {
                get_prompt_print("InvalidChoice");
                invalidChoices++;
                continue;
            }
            
            if (choice == 4) {
                // Exit condition
                running = false;
            } else if (choice < 1 || choice > 3) {
                get_prompt_print("InvalidChoice");
                invalidChoices++;
            } else {
                // Valid choice, reset invalid counter
                invalidChoices = 0;
                
                switch (choice) {
                    case 1:
                        // View account details such as acc number, associated users and their details
                        view_account_details(account);
                        break;
                    case 2:
                        // View account balance
                        view_account_balance(account);
                        break;
                    case 3:
                        // View transaction history
                        view_transaction_history(account);
                        break;
                }
            }
        }
        
        if (invalidChoices >= max_invalid_attempts) {
            get_prompt_print("TooManyInvalidChoices");
        }
    }

    private void view_account_details(Accounts account) {
        // Implementation for viewing account details
    }

    private void view_account_balance(Accounts account) {
        // Implementation for viewing account balance
        ViewBalance viewBalance = new ViewBalance(db_interface);
        viewBalance.run(account);
    }

    private void view_transaction_history(Accounts account) {
        // Implementation for viewing transaction history
    }
}
