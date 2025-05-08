package src.Gui.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Gui.ComponentBase;
import src.Gui.MainWindow;

public class LoginPage extends ComponentBase {
    private String username;
    private String password;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage(MainWindow main_window) {
        super(main_window);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                password = new String(passwordField.getPassword());
            }
        });
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}