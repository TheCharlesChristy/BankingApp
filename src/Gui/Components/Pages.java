package src.Gui.Components;

import src.Gui.MainWindow;
import src.Gui.Components.UserDashboard.BankingInterface;
import src.Gui.Components.AdminDashboard.AdminDashboard;
import src.Gui.Components.AdminDashboard.ManageUserPage;
import src.Gui.Components.AdminDashboard.AdminDepositPage;
import src.Gui.Components.AdminDashboard.AdminWithdrawPage;
import src.Gui.Components.AdminDashboard.AdminTransferPage;


public class Pages {
    public WelcomePage WELCOME;
    public LoginPage LOGIN;
    public RegisterPage REGISTER;
    public BankingInterface BANKING_INTERFACE;
    public DepositPage DEPOSIT;
    public WithdrawPage WITHDRAW;
    public TransferPage TRANSFER;
    public AdminDashboard ADMIN_DASHBOARD;
    public ManageUserPage MANAGE_USER_PAGE;
    public AdminDepositPage ADMIN_DEPOSIT;
    public AdminWithdrawPage ADMIN_WITHDRAW;
    public AdminTransferPage ADMIN_TRANSFER;

    public Pages(MainWindow main_window) {
        WELCOME = new WelcomePage(main_window);
        LOGIN = new LoginPage(main_window);
        REGISTER = new RegisterPage(main_window);
        BANKING_INTERFACE = new BankingInterface(main_window);
        DEPOSIT = new DepositPage(main_window);
        WITHDRAW = new WithdrawPage(main_window);
        TRANSFER = new TransferPage(main_window);
        ADMIN_DASHBOARD = new AdminDashboard(main_window);
        MANAGE_USER_PAGE = new ManageUserPage(main_window);
        ADMIN_DEPOSIT = new AdminDepositPage(main_window);
        ADMIN_WITHDRAW = new AdminWithdrawPage(main_window);
        ADMIN_TRANSFER = new AdminTransferPage(main_window);
    }
}
