package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;

public class Components {
    private DataBaseInterface db_interface;
    public MainMenu main_menu;
    public Login login;
    public Register register;
    public AccountManager account_manager;
    public AdminAccountManager admin_account_manager;
    public ViewBalance view_balance;
    public Deposit deposit;
    public Withdraw withdraw;
    public Transfer transfer;
    public AdminSearchForUser admin_search_for_user;
    public AdminManageAccount admin_manage_account;

    public Components(DataBaseInterface db_interface) {
        this.db_interface = db_interface;
        this.main_menu = new MainMenu(db_interface);
        this.login = new Login(db_interface);
        this.register = new Register(db_interface);
        this.account_manager = new AccountManager(db_interface);
        this.admin_account_manager = new AdminAccountManager(db_interface);
        this.view_balance = new ViewBalance(db_interface);
        this.deposit = new Deposit(db_interface);
        this.withdraw = new Withdraw(db_interface);
        this.transfer = new Transfer(db_interface);
        this.admin_search_for_user = new AdminSearchForUser(db_interface);
        this.admin_manage_account = new AdminManageAccount(db_interface);
    }
}
