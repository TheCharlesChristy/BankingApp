package src.Gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import src.Gui.Components.Pages;

public class MainWindow extends JFrame {
    private JPanel content;
    public Pages pages;

    public MainWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.pages = new Pages(this);
        this.setContent(pages.LOGIN);
    }

    public void setContent(JPanel newContent) {
        if (content != null) {
            remove(content);
        }
        content = newContent;
        add(content, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public JPanel getContent() {
        return content;
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }

    public String getTitle() {
        return super.getTitle();
    }

    public void gotoLogin() {
        setContent(pages.LOGIN);
        setTitle("Login");
    }

    public void gotoRegister() {
        setContent(pages.REGISTER);
        setTitle("Register");
    }

    public void gotoBankingInterface() {
        setContent(pages.BANKING_INTERFACE);
        setTitle("Banking Interface");
    }

    public void gotoDeposit() {
        setContent(pages.DEPOSIT);
        setTitle("Deposit");
    }

    public void gotoWithdraw() {
        setContent(pages.WITHDRAW);
        setTitle("Withdraw");
    }

    public void gotoTransfer() {
        setContent(pages.TRANSFER);
        setTitle("Transfer");
    }

    public void gotoAdminDashboard() {
        setContent(pages.ADMIN_DASHBOARD);
        setTitle("Admin Dashboard");
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow("Main Window");
        window.setVisible(true);
        window.gotoAdminDashboard();
    }
}
