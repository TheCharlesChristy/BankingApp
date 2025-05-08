package src.Server.CommandLine;

import src.Server.DataBaseInterface;

import src.Server.CommandLine.Components.Components;
import src.IOHandler;

import src.Structs.Users;
import src.Structs.Accounts;

public class CommandLineInterface {
    DataBaseInterface db_interface;
    public Components components;
    private IOHandler io = new IOHandler();

    public CommandLineInterface(DataBaseInterface db_interface) {
        this.db_interface = db_interface;
        this.components = new Components(db_interface);
        this.io = new IOHandler();
    }

    private void admin_account_main_loop(Users usr) {
        while (true) {
            int choice = components.admin_account_manager.run();
            if (choice == 1) {
                // Search for user
                Accounts account = components.admin_search_for_user.run();
                if (account != null) {
                    components.admin_manage_account.run(account);
                } else {
                    io.println("No account found.");
                }
            } else {
                // Logout
                io.debug("Logging out of admin account...");
                break;
            }
        }
    }

    private void account_main_loop(Users usr) {
        while (true) {
            int choice = components.account_manager.run();
            Accounts account = db_interface.account_interface.get_account_by_uid(usr.get_id());
            if (choice == 1) {
                // view account
                components.view_balance.set_user(usr);
                components.view_balance.run(usr);
                components.view_balance.clear();
            } else if (choice == 2) {
                // deposit
                components.deposit.set_user(usr);
                components.deposit.set_account(account);
                components.deposit.run();
                components.deposit.clear();
            } else if (choice == 3) {
                // withdraw
                components.withdraw.set_user(usr);
                components.withdraw.set_account(account);
                components.withdraw.run();
                components.withdraw.clear();
            } else if (choice == 4) {
                // transfer
                components.transfer.run(account);
            } else {
                // Logout
                io.debug("Logging out of account...");
                break;
            }
        }
    }

    private void main_menu_loop() {
        while (true) {
            int choice = components.main_menu.run();
            if (choice == 1) {
                // Attempt login and get user object
                Users usr = components.login.run();
                if (usr != null) {
                    // Check if the user is an admin or regular user
                    if (db_interface.admins_interface.is_admin(usr.get_username())){
                        io.debug("Admin account detected, entering admin mode...");
                        admin_account_main_loop(usr);
                    }else {
                        account_main_loop(usr);
                    }
                }
            } else if (choice == 2) {
                components.register.run();
            } else {
                break;
            }
        }
    }

    public void run() {
        main_menu_loop();
    }
}
