package src.Gui.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.Gui.ComponentBase;
import src.Gui.MainWindow;
import src.Structs.Accounts;

public class RegisterPage extends ComponentBase {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public RegisterPage(MainWindow main_window) {
        super(main_window);
        setLayout(new BorderLayout());

        // Top panel with Back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_back();
            }
        });

        // Center panel for form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(usernameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(emailField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(passwordField, gbc);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(confirmPassLabel, gbc);

        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(confirmPasswordField, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        registerButton.addActionListener(_ -> {
            username = usernameField.getText();
            email = emailField.getText();
            password = new String(passwordField.getPassword());
            confirmPassword = new String(confirmPasswordField.getPassword());
            if(fieldCheck()){
                register();
            }
        });

        add(formPanel, BorderLayout.CENTER);
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    public boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check for uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one uppercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check for lowercase letter
        if (!password.matches(".*[a-z].*")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one lowercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check for digit
        if (!password.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one digit", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check for special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one special character", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    public boolean fieldCheck() {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    public void register() {
        Accounts acc = main_window.cmd_functions.register(username, confirmPassword, email);
        if (acc != null) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            main_window.gotoLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void go_back() {
        if (main_window != null) {
            main_window.gotoWelcomePage();
        }else {
            // Exit the application if main_window is null
            System.exit(0);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
