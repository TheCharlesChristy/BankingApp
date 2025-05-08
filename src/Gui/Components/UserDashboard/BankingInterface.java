package src.Gui.Components.UserDashboard;

import src.Gui.MainWindow;
import src.Gui.ComponentBase;
import javax.swing.*;
import java.awt.*;

public class BankingInterface extends ComponentBase {
    public BankingInterface(MainWindow window) {
        super(window);
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Example placeholders for username, balance, and currency
        String username = "User";
        double balance = 1000.00;
        String currency = "USD";
        AccountBalance accountBalance = new AccountBalance(window, username, balance, currency);
        AccountTransaction accountTransaction = new AccountTransaction(window);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setBackground(new Color(0xCCCCCC));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(_ -> logout());

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(logoutPanel, BorderLayout.NORTH);
        topPanel.add(accountBalance, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(accountTransaction, BorderLayout.CENTER);
    }

    private void logout() {
        main_window.gotoLogin();
    }
}
