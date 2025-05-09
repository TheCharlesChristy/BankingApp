package src.Gui.Components.UserDashboard;

import src.Gui.MainWindow;
import src.Gui.ComponentBase;
import javax.swing.*;
import java.awt.*;

public class AccountBalance extends ComponentBase {
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JComboBox<String> currencyDropdown;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton transferButton;
    private String[] currencies = {"USD", "EUR", "GBP"};

    public AccountBalance(MainWindow window, String username, double balance, String currency) {
        super(window);
        setLayout(new BorderLayout(10, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setOpaque(false);
        welcomeLabel = new JLabel("Welcome " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        // Balance panel with currency selector
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        balancePanel.setOpaque(false);
        balanceLabel = new JLabel("Balance: " + String.format("%.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        currencyDropdown = new JComboBox<>(currencies);
        currencyDropdown.setFont(new Font("Arial", Font.PLAIN, 16));
        currencyDropdown.setSelectedItem(currency);
        currencyDropdown.setFocusable(false);
        currencyDropdown.setMaximumSize(new Dimension(80, 30));
        balancePanel.add(balanceLabel);
        balancePanel.add(currencyDropdown);
        leftPanel.add(welcomeLabel);
        leftPanel.add(balancePanel);
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        withdrawButton = createActionButton("Withdraw");
        depositButton = createActionButton("Deposit");
        transferButton = createActionButton("Transfer");
        rightPanel.add(withdrawButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(depositButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(transferButton);
        add(rightPanel, BorderLayout.EAST);

        currencyDropdown.addActionListener(e -> on_currency_changed());
        withdrawButton.addActionListener(e -> on_withdraw());
        depositButton.addActionListener(e -> on_deposit());
        transferButton.addActionListener(e -> on_transfer());
        setPreferredSize(new Dimension(760, 120));
    }

    private void on_currency_changed() {
        System.out.println("Currency changed to: " + currencyDropdown.getSelectedItem());
    }

    private void on_deposit() {
        main_window.gotoDeposit();
    }

    private void on_withdraw() {
        main_window.gotoWithdraw();
    }

    private void on_transfer() {
        main_window.gotoTransfer();
    }

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.setBackground(new Color(0x3c3c3c));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 40));
        return btn;
    }
}
