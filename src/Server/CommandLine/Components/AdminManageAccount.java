package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

import src.Structs.Accounts;

public class AdminManageAccount extends CommandLineFunctions {
    AdminViewAccount view_account;
    AdminEditAccount edit_account;


    public AdminManageAccount(DataBaseInterface db_interface) {
        super(db_interface);
        this.view_account = new AdminViewAccount(db_interface);
        this.edit_account = new AdminEditAccount(db_interface);
    }

    public void run(Accounts account) {
        get_prompt_print("AdminManageAccount");
        boolean running = true;
        while (running) {
            int choice = get_prompt_enter("AdminManageAccountChoice").charAt(0) - '0';
            switch (choice) {
            case 1:
                // View account details
                view_account_details(account);
                break;
            case 2:
                // Edit account details
                edit_account_details(account);
                break;
            case 3:
                // Delete account
                delete_account(account);
                // Dont break, as we need to exit the menu after deletion
            case 4:
                // Exit the management menu
                running = false;
                break;
            default:
                get_prompt_print("InvalidChoice");
            }
        }
    }

    private void view_account_details(Accounts account) {
        // Implementation for viewing account details
        view_account.run(account);
    }

    private void edit_account_details(Accounts account) {
        // Implementation for editing account details
        edit_account.run(account);
    }

    private void delete_account(Accounts account) {
        // Implementation for deleting an account
        // Prompt Confirmation before deletion
        String confirmation = get_prompt_enter("AdminDeleteAccountConfirmation");
        if (confirmation.equalsIgnoreCase("y")) {
            // Call the database interface to delete the account
            db_interface.account_interface.delete_account(account.get_account_id());
            get_prompt_print("AdminDeleteAccountSuccess");
        } else {
            get_prompt_print("AdminDeleteAccountCancelled");
        }
    }
}
