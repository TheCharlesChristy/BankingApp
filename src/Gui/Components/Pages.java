package src.Gui.Components;

import src.Gui.MainWindow;

public class Pages {
    public LoginPage LOGIN;
    public RegisterPage REGISTER;

    public Pages(MainWindow main_window) {
        LOGIN = new LoginPage(main_window);
        REGISTER = new RegisterPage(main_window);
    }
}
