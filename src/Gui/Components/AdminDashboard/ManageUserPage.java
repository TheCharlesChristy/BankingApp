package src.Gui.Components.AdminDashboard;

import src.Gui.ComponentBase;
import src.Gui.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageUserPage extends ComponentBase {
    private JTextField usernameField;
    private JTextField emailField;
    private JLabel balanceLabel;
    private JComboBox<String> currencyDropdown;
    private JTable transactionTable;
    private DefaultTableModel transactionTableModel;
    private JButton saveUsernameButton;
    private JButton saveEmailButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton transactionButton;
    private String[] currencies = {"USD", "EUR", "GBP"};

    public ManageUserPage(MainWindow main_window) {
        super(main_window);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Logout button at the top right
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> onLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        topPanel.add(logoutPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.PAGE_START);

        // --- Refactored layout for improved aesthetics ---
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setOpaque(false);
        userPanel.setBorder(BorderFactory.createTitledBorder("User Information"));

        // Username row
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        usernamePanel.setOpaque(false);
        usernamePanel.add(new JLabel("Username:"));
        usernameField = new JTextField("username", 15); // Placeholder
        usernamePanel.add(usernameField);
        saveUsernameButton = new JButton("Save");
        saveUsernameButton.addActionListener(e -> onSaveUsername());
        usernamePanel.add(saveUsernameButton);
        userPanel.add(usernamePanel);
        userPanel.add(Box.createVerticalStrut(10));

        // Email row
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        emailPanel.setOpaque(false);
        emailPanel.add(new JLabel("Email:"));
        emailField = new JTextField("user@email.com", 15); // Placeholder
        emailPanel.add(emailField);
        saveEmailButton = new JButton("Save");
        saveEmailButton.addActionListener(e -> onSaveEmail());
        emailPanel.add(saveEmailButton);
        userPanel.add(emailPanel);
        userPanel.add(Box.createVerticalStrut(10));

        // Account Balance row
        JPanel balanceRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        balanceRowPanel.setOpaque(false);
        balanceRowPanel.add(new JLabel("Account Balance:"));
        balanceLabel = new JLabel(getBalanceString(1000.00f, "USD")); // Placeholder
        balanceRowPanel.add(balanceLabel);
        currencyDropdown = new JComboBox<>(currencies);
        currencyDropdown.setSelectedItem("USD");
        currencyDropdown.addActionListener(e -> onCurrencyChanged());
        balanceRowPanel.add(currencyDropdown);
        userPanel.add(balanceRowPanel);
        userPanel.add(Box.createVerticalStrut(10));

        // Account Actions section
        JPanel balanceButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        balanceButtonsPanel.setOpaque(false);
        depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> onDeposit());
        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> onWithdraw());
        transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> transfer());
        balanceButtonsPanel.add(depositButton);
        balanceButtonsPanel.add(withdrawButton);
        balanceButtonsPanel.add(transferButton);
        userPanel.add(balanceButtonsPanel);
        userPanel.add(Box.createVerticalStrut(10));

        // --- Add userPanel to a wrapper panel and add to BorderLayout.CENTER ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(userPanel, BorderLayout.NORTH);

        // Transaction History
        JPanel transactionPanel = new JPanel(new BorderLayout());
        transactionPanel.setOpaque(false);
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        JLabel transactionLabel = new JLabel("Transaction History");
        transactionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        transactionPanel.add(transactionLabel, BorderLayout.NORTH);
        String[] columns = {"Amount", "Type", "Date"};
        transactionTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        transactionTable = new JTable(transactionTableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        transactionPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(transactionPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        loadTransactionHistory();
    }

    private void onSaveUsername() {
        String newUsername = usernameField.getText().trim();
        System.out.println("Save username: " + newUsername);
    }

    private void onSaveEmail() {
        String newEmail = emailField.getText().trim();
        System.out.println("Save email: " + newEmail);
    }

    private void onDeposit() {
        System.out.println("Deposit button pressed");
    }

    private void onWithdraw() {
        System.out.println("Withdraw button pressed");
    }

    private void transfer() {
        System.out.println("Transfer button pressed");
    }

    private void onCurrencyChanged() {
        String selected = (String) currencyDropdown.getSelectedItem();
        System.out.println("Currency changed to: " + selected);
        balanceLabel.setText(getBalanceString(1000.00f, selected)); // Placeholder
    }

    private String getBalanceString(float balance, String currency) {
        return String.format("%.2f %s", balance, currency);
    }

    private void loadTransactionHistory() {
        transactionTableModel.setRowCount(0);
        transactionTableModel.addRow(new Object[]{"+500.00", "Deposit", "2025-05-01"});
        transactionTableModel.addRow(new Object[]{"-100.00", "Withdrawal", "2025-05-02"});
        transactionTableModel.addRow(new Object[]{"-50.00", "Transfer", "2025-05-03"});
    }

    private void onLogout() {
        System.out.println("Logout button pressed");
        // Example: main_window.gotoLogin();
    }
}
