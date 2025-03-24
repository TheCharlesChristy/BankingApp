package src.Server.CommandLine;

import src.Server.DataBaseInterface;

import src.Server.CommandLine.Components.Login;
import src.Server.CommandLine.Components.Register; 
import src.Server.CommandLine.Components.AccountManager; 
import src.Server.CommandLine.Components.MainMenu; 
import src.Server.CommandLine.Components.ViewBalance; 

import src.Structs.Users;
import src.Structs.Accounts;

public class CommandLineInterface {
    DataBaseInterface db_interface;
    public MainMenu main_menu;
    public Login login;
    public Register register;
    public AccountManager account_manager;
    public ViewBalance view_balance;

    public CommandLineInterface(DataBaseInterface db_interface) {
        this.login = new Login(db_interface);
        this.register = new Register(db_interface);
        this.account_manager = new AccountManager(db_interface);
        this.main_menu = new MainMenu(db_interface);
        this.view_balance = new ViewBalance(db_interface);
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
                    account_main_loop(usr);
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
