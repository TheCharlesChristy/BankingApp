package src.Gui.Components;

import src.Gui.ComponentBase;
import src.Gui.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalFinancePageBase extends ComponentBase {
    protected JLabel titleLabel;
    protected JTextField amountField;
    protected JButton submitButton;
    protected JComboBox<String> currencyDropdown;
    protected JButton goBackButton;
    protected String[] currencies = {"USD", "EUR", "JPY", "GBP"};

    public PersonalFinancePageBase(MainWindow main_window) {
        super(main_window);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        titleLabel = new JLabel("Personal Finance Action", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Add currency dropdown
        JLabel currencyLabel = new JLabel("Currency:");
        currencyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(currencyLabel, gbc);

        currencyDropdown = new JComboBox<>(currencies);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(currencyDropdown, gbc);

        // Shift amount label and field down
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(amountLabel, gbc);

        amountField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(amountField, gbc);

        submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSubmit();
            }
        });

        // Add Go Back button
        goBackButton = new JButton("Go Back");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(goBackButton, gbc);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_back();
            }
        });
    }

    protected void onSubmit() {
        // To be overridden by subclasses
    }

    protected void go_back() {
        main_window.gotoBankingInterface();
    }

    public double getAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    public String getSelectedCurrency() {
        return (String) currencyDropdown.getSelectedItem();
    }
}
