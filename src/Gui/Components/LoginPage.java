package src.Gui.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Gui.ComponentBase;
import src.Gui.MainWindow;
import src.Structs.Accounts;
import src.Structs.UserInstance;
import src.Structs.Users;

public class LoginPage extends ComponentBase {
    private String username;
    private String password;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage(MainWindow main_window) {
        super(main_window);
        setLayout(new BorderLayout());

        // Back button at the top left
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_back();
            }
        });
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Register button below Login button
        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoRegister();
            }
        });

        add(centerPanel, BorderLayout.CENTER);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void login() {
        // Get the username and password from the text fields
        username = usernameField.getText();
        password = new String(passwordField.getPassword());

        Users user = main_window.cmd_functions.login_user(username, password);

        if (user != null) {
            // Get the user instance from the account
            UserInstance user_instance = main_window.cmd_functions.get_user_instance(user);
            main_window.setCurrentUserInstance(user_instance);
            
            // if the user is an admin goto the admin page
            if (user_instance.is_admin) {
                main_window.gotoAdminDashboard();
            } else {
                // Otherwise, go to the user page
                main_window.gotoBankingInterface();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void gotoRegister() {
        if (main_window != null) {
            main_window.gotoRegister();
        } else {
            // Exit the application if main_window is null
            System.exit(0);
        }
    }

    public void go_back() {
        if (main_window != null) {
            main_window.gotoWelcomePage();
        } else {
            // Exit the application if main_window is null
            System.exit(0);
        }
    }
}