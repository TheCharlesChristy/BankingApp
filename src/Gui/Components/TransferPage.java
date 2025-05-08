package src.Gui.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.Gui.ComponentBase;
import src.Gui.MainWindow;

public class TransferPage extends ComponentBase {
    private JComboBox<String> accountDropdown;
    private JSpinner amountSpinner;
    private JComboBox<String> currencyDropdown;
    private JButton submitButton;
    private JButton backButton;

    public TransferPage(MainWindow main_window) {
        super(main_window);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Top panel with Go Back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Go Back");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(e -> go_back());

        // Center panel for form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Target Account Dropdown
        JLabel accountLabel = new JLabel("Target Account:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(accountLabel, gbc);
        accountDropdown = new JComboBox<>(new String[]{"Account 1", "Account 2", "Account 3"});
        gbc.gridx = 1;
        formPanel.add(accountDropdown, gbc);

        // Amount Spinner
        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(amountLabel, gbc);
        amountSpinner = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1000000.00, 1.00));
        gbc.gridx = 1;
        formPanel.add(amountSpinner, gbc);

        // Currency Dropdown
        JLabel currencyLabel = new JLabel("Currency:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(currencyLabel, gbc);
        currencyDropdown = new JComboBox<>(new String[]{"USD", "EUR", "GBP"});
        gbc.gridx = 1;
        formPanel.add(currencyDropdown, gbc);

        // Submit Button
        submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(submitButton, gbc);

        submitButton.addActionListener(e -> submit());

        add(formPanel, BorderLayout.CENTER);
    }

    private void go_back() {
        if (main_window != null) {
            main_window.gotoBankingInterface();
        }
    }

    private void submit() {
        String targetAccount = (String) accountDropdown.getSelectedItem();
        double amount = (Double) amountSpinner.getValue();
        String currency = (String) currencyDropdown.getSelectedItem();
        // TODO: Implement transfer logic
        JOptionPane.showMessageDialog(this, "Transfer submitted to " + targetAccount + " for " + amount + " " + currency);
    }
}
