// AccountBalance component for user dashboard
package src.Gui.Components.UserDashboard;

import src.Gui.MainWindow;
import src.Structs.UserInstance;
import src.Gui.ComponentBase;
import javax.swing.*;
import java.awt.*;
import src.Structs.Currency;

public class AccountBalance extends ComponentBase {
    // UI components
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JComboBox<String> currencyDropdown;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton transferButton;
    private String[] currencies = {"USD", "EUR", "JPY", "GBP"};

    // Constructor: sets up layout and event handlers
    public AccountBalance(MainWindow window) {
        super(window);
        setLayout(new BorderLayout(10, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if (window.getCurrentUserInstance() == null) {
            System.out.println("Error: current_user_instance is null");
            return;
        }else {
            System.out.println("Current user instance: " + window.getCurrentUserInstance().user.username);
        }

        // Left panel: welcome and balance
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setOpaque(false);
        welcomeLabel = new JLabel("Welcome " + main_window.getCurrentUserInstance().user.username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Balance label
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        balancePanel.setOpaque(false);
        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        // update_account_balance(Currency.USD);

        // Currency dropdown    
        currencyDropdown = new JComboBox<>(currencies);
        currencyDropdown.setFont(new Font("Arial", Font.PLAIN, 16));
        currencyDropdown.setSelectedItem("USD");
        currencyDropdown.setFocusable(false);
        currencyDropdown.setMaximumSize(new Dimension(80, 30));

        // Add components to panels
        balancePanel.add(balanceLabel);
        balancePanel.add(currencyDropdown);
        leftPanel.add(welcomeLabel);
        leftPanel.add(balancePanel);
        add(leftPanel, BorderLayout.WEST);

        // Right panel: action buttons
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

        // Event listeners
        currencyDropdown.addActionListener(_ -> on_currency_changed());
        withdrawButton.addActionListener(_ -> on_withdraw());
        depositButton.addActionListener(_ -> on_deposit());
        transferButton.addActionListener(_ -> on_transfer());
        setPreferredSize(new Dimension(760, 120));
    }

    // Update balance label
    private void update_account_balance(float new_balance) {
        balanceLabel.setText("Balance: " + String.format("%.2f", new_balance));
    }

    // Update balance label
    private void update_account_balance(Currency currency) {
        // Get the current user instance and update the balance
        UserInstance user_instance = main_window.getCurrentUserInstance();
        if (user_instance != null) {
            float balance_usd = user_instance.account.balance;
            float balance_converted = main_window.cmd_functions.convert_to_currency(
                balance_usd, currency
            );
            update_account_balance(balance_converted);
        }
    }

    // Currency dropdown handler
    private void on_currency_changed() {
        System.out.println("Currency changed to: " + currencyDropdown.getSelectedItem());
    }

    // Deposit button handler
    private void on_deposit() {
        main_window.gotoDeposit();
    }

    // Withdraw button handler
    private void on_withdraw() {
        main_window.gotoWithdraw();
    }

    // Transfer button handler
    private void on_transfer() {
        main_window.gotoTransfer();
    }

    // Helper to create styled action buttons
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
