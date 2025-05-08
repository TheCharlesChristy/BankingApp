package src.Gui.Components;

import src.Gui.MainWindow;
import src.Gui.Components.UserDashboard.BankingInterface;;

public class Pages {
    public LoginPage LOGIN;
    public RegisterPage REGISTER;
    public BankingInterface BANKING_INTERFACE;

    public Pages(MainWindow main_window) {
        LOGIN = new LoginPage(main_window);
        REGISTER = new RegisterPage(main_window);
        BANKING_INTERFACE = new BankingInterface(main_window);
    }
}
