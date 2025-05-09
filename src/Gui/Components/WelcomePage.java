package src.Gui.Components;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Gui.ComponentBase;
import src.Gui.MainWindow;

public class WelcomePage extends ComponentBase {
    public WelcomePage(MainWindow main_window) {
        super(main_window);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Welcome");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        add(loginButton, gbc);

        gbc.gridx = 1;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        add(registerButton, gbc);
    }

    private void login() {
        main_window.gotoLogin();
    }

    private void register() {
        main_window.gotoRegister();
    }
}
