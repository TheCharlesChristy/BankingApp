package src.Gui.Components;

import src.Gui.MainWindow;
import javax.swing.*;

public class WithdrawPage extends PersonalFinancePageBase {
    public WithdrawPage(MainWindow main_window) {
        super(main_window);
        titleLabel.setText("Withdraw Money");
        submitButton.setText("Withdraw");
    }

    @Override
    protected void onSubmit() {
        double amount = getAmount();
        if (amount > 0) {
            // TODO: Implement withdrawal logic here
            JOptionPane.showMessageDialog(this, "Withdrew $" + String.format("%.2f", amount), "Withdraw", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
