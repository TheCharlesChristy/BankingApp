package src.Server.CommandLine;

import src.Server.DataBaseInterface;

import src.Server.CommandLine.Components.Login;
import src.Server.CommandLine.Components.Register; 
import src.Server.CommandLine.Components.AccountManager; 
import src.Server.CommandLine.Components.AdminAccountManager; 
import src.Server.CommandLine.Components.MainMenu; 
import src.Server.CommandLine.Components.ViewBalance; 
import src.IOHandler;

import src.Structs.Users;
import src.Structs.Accounts;

public class CommandLineInterface {
    DataBaseInterface db_interface;
    public MainMenu main_menu;
    public Login login;
    public Register register;
    public AccountManager account_manager;
    public AdminAccountManager admin_account_manager;
    public ViewBalance view_balance;
    private IOHandler io = new IOHandler();

    public CommandLineInterface(DataBaseInterface db_interface) {
        this.db_interface = db_interface;
        this.login = new Login(db_interface);
        this.register = new Register(db_interface);
        this.account_manager = new AccountManager(db_interface);
        this.admin_account_manager = new AdminAccountManager(db_interface);
        this.main_menu = new MainMenu(db_interface);
        this.view_balance = new ViewBalance(db_interface);
        this.io = new IOHandler();
    }

    private void admin_account_main_loop(Users usr) {
        while (true) {
            int choice = admin_account_manager.run();
            if (choice == 1) {
                // Search for user
                break;
            } else {
                // Logout
                io.debug("Logging out of admin account...");
                break;
            }
        }
    }

    private void account_main_loop(Users usr) {
        while (true) {
            int choice = account_manager.run();
            if (choice == 1) {
                // view account
                view_balance.set_user(usr);
                view_balance.run(usr);
            } else if (choice == 2) {
                // deposit
                break;
            } else if (choice == 3) {
                // withdraw
                break;
            } else if (choice == 4) {
                // transfer
                break;
            } else {
                break;
            }
        }
    }

    private void main_menu_loop() {
        while (true) {
            int choice = main_menu.run();
            if (choice == 1) {
                Users usr = login.run();
                if (usr != null) {
                    if (db_interface.admins_interface.is_admin(usr.get_id())){
                        io.debug("Admin account detected, entering admin mode...");
                        admin_account_main_loop(usr);
                    }else {
                        account_main_loop(usr);
                    }
                }
            } else if (choice == 2) {
                register.run();
            } else {
                break;
            }
        }
    }

    public void run() {
        main_menu_loop();
    }
}
