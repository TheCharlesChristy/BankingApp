package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

import src.Structs.Accounts;
import src.Structs.Users;

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
        if (choice < 1 || choice > 4) {
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
            case 3:
                // Exit condition
                get_prompt_print("ExitingEditAccount");
                break;
            default:
                get_prompt_print("InvalidChoice");
        }
    }   

    private void edit_account_details(Accounts account) {
        get_prompt_print("EditAccountDetailsHeader");
        io.println("Current Account ID: " + account.get_account_id());
        
        // Get and display current user information
        Users currentUser = db_interface.user_interface.get_user(account.user_id);
        String currentUsername = currentUser != null ? currentUser.get_username() : "Unknown User";
        io.println("Current Owner Username: " + currentUsername);
        
        // Prompt for new username
        String new_username = get_prompt_enter("EditAccountDetailsNewUsername");
        
        // Check if input is empty (cancel operation)
        if (new_username == null || new_username.trim().isEmpty()) {
            get_prompt_print("EditAccountDetailsCancelled");
            return;
        }
        
        // Verify new username exists
        Users new_user = db_interface.user_interface.get_user(new_username);
        if (new_user == null) {
            get_prompt_print("EditAccountDetailsUserNotFound");
            return;
        }
        
        // Confirm the change
        io.println("\nChange account owner from '" + currentUsername + "' to '" + new_username + "'?");
        String confirm = get_prompt_enter("EditAccountDetailsConfirm");
        
        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            // Update the account's user_id
            int old_user_id = account.user_id;
            account.user_id = new_user.get_id();

            // Print out the old and new user IDs
            io.println("Old User ID: " + old_user_id);
            io.println("New User ID: " + new_user.get_id());
            
            // Save changes to database
            try {
                db_interface.account_interface.update_account(account);
                get_prompt_print("EditAccountDetailsSuccess");
                io.println("Account owner changed from '" + currentUsername + "' to '" + new_username + "'.");
            } catch (Exception e) {
                // Restore the old user_id if update fails
                account.user_id = old_user_id;
                get_prompt_print("EditAccountDetailsError");
                io.println(e.getMessage());
            }
        } else {
            get_prompt_print("EditAccountDetailsCancelled");
        }
        
        get_prompt_enter("PressEnterToContinue");
    }

    private void edit_account_balance(Accounts account) {
        // Implementation for editting account balance
        get_prompt_print("EditAccountBalanceHeader");
        io.println("Current Balance: " + account.get_balance());

        // ask if the user wants to deposit or withdraw
        String choice = get_prompt_enter("EditAccountBalanceChoice");
        if (choice.equals("1")) {
            // Deposit
            Deposit deposit = new Deposit(db_interface);
            deposit.set_account(account);
            deposit.run();
        } else if (choice.equals("2")) {
            // Withdraw
            Withdraw withdraw = new Withdraw(db_interface);
            withdraw.set_account(account);
            withdraw.run();
        } else if (choice.equals("3")) {
            // Transfer
            Transfer transfer = new Transfer(db_interface);
            transfer.run(account);
        } else if (choice.equals("4")) {
            // Cancel
            get_prompt_print("EditAccountBalanceCancelled");
            return;
        } else {
            get_prompt_print("InvalidChoice");
        }

        io.println("New Balance: " + account.get_balance());
        get_prompt_enter("PressEnterToContinue");
        get_prompt_print("EditAccountBalanceExit");
    }
}
