package src.Gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import src.Gui.Components.Pages;

import src.Structs.UserInstance;
import src.Server.CommandLine.CommandLineFunctions;
import src.Server.DataBaseInterface;

public class MainWindow extends JFrame {
    private JPanel content;
    public Pages pages;

    public static UserInstance current_user_instance;
    public static UserInstance admin_search_for_user_instance;
    public static UserInstance target_user_instance;

    public CommandLineFunctions cmd_functions;

    public MainWindow(String title, DataBaseInterface db_interface) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.pages = new Pages(this);
        this.setContent(pages.LOGIN);

        this.cmd_functions = new CommandLineFunctions(db_interface);
    }

    public void setContent(JPanel newContent) {
        clear_pages();
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

    public void setCurrentUserInstance(UserInstance user_instance) {
        MainWindow.current_user_instance = user_instance;
    }

    public UserInstance getCurrentUserInstance() {
        return current_user_instance;
    }

    public void setAdminSearchForUserInstance(UserInstance user_instance) {
        MainWindow.admin_search_for_user_instance = user_instance;
    }

    public UserInstance getAdminSearchForUserInstance() {
        return admin_search_for_user_instance;
    }

    public void setTargetUserInstance(UserInstance user_instance) {
        MainWindow.target_user_instance = user_instance;
    }

    public UserInstance getTargetUserInstance() {
        return target_user_instance;
    }

    private void clear_pages() {
        pages.clear(this);
    }

    public void gotoWelcomePage() {
        setContent(pages.WELCOME);
        setTitle("Welcome");
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
        System.out.println("Banking Interface");
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

    public void gotoManageUserPage() {
        setContent(pages.MANAGE_USER_PAGE);
        setTitle("Manage User Page");
    }

    public void gotoAdminDeposit() {
        setContent(pages.ADMIN_DEPOSIT);
        setTitle("Admin Deposit");
    }

    public void gotoAdminWithdraw() {
        setContent(pages.ADMIN_WITHDRAW);
        setTitle("Admin Withdraw");
    }

    public void gotoAdminTransfer() {
        setContent(pages.ADMIN_TRANSFER);
        setTitle("Admin Transfer");
    }

    public static void main(String[] args) {
        DataBaseInterface db_interface = new DataBaseInterface();
        MainWindow window = new MainWindow("Main Window", db_interface);
        window.setVisible(true);
        window.gotoWelcomePage();
    }
}
