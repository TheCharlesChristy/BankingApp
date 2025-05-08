package src.Gui.Components;

import src.Gui.MainWindow;
import javax.swing.*;

public class DepositPage extends PersonalFinancePageBase {
    public DepositPage(MainWindow main_window) {
        super(main_window);
        titleLabel.setText("Deposit Money");
        submitButton.setText("Deposit");
    }

    @Override
    protected void onSubmit() {
        double amount = getAmount();
        if (amount > 0) {
            // TODO: Implement deposit logic here
            JOptionPane.showMessageDialog(this, "Deposited $" + String.format("%.2f", amount), "Deposit", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
