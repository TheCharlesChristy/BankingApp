package src.Gui.Components.AdminDashboard;

import src.Gui.Components.WithdrawPage;
import src.Gui.MainWindow;

public class AdminWithdrawPage extends WithdrawPage {
    public AdminWithdrawPage(MainWindow main_window) {
        super(main_window);
    }

    @Override
    protected void go_back() {
        if (main_window != null) {
            main_window.gotoManageUserPage();
        }
    }
}
