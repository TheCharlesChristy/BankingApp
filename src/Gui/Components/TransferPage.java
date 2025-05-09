package src.Gui.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.Gui.ComponentBase;
import src.Gui.MainWindow;

public class TransferPage extends ComponentBase {
    private JTextField accountNumberField;
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

        // Target Account Number Input
        JLabel accountLabel = new JLabel("Target Account Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(accountLabel, gbc);
        accountNumberField = new JTextField(12);
        gbc.gridx = 1;
        formPanel.add(accountNumberField, gbc);

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

    protected void go_back() {
        if (main_window != null) {
            main_window.gotoBankingInterface();
        }
    }

    private void submit() {
        String input = accountNumberField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an account number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int accountNum = Integer.parseInt(input);
            double amount = (Double) amountSpinner.getValue();
            String currency = (String) currencyDropdown.getSelectedItem();
            // TODO: Lookup account by number and implement transfer logic
            JOptionPane.showMessageDialog(this, "Transfer submitted to account #" + accountNum + " for " + amount + " " + currency);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid account number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
