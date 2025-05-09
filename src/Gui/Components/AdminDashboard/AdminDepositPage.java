package src.Gui.Components.AdminDashboard;

import src.Gui.Components.DepositPage;
import src.Gui.MainWindow;

public class AdminDepositPage extends DepositPage {
    public AdminDepositPage(MainWindow main_window) {
        super(main_window);
    }

    @Override
    protected void go_back() {
        if (main_window != null) {
            main_window.gotoManageUserPage();
        }
    }
}
