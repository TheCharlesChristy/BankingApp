package src.Gui.Components.AdminDashboard;

import src.Gui.ComponentBase;
import src.Gui.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// JFreeChart imports (requires jfreechart and jcommon jars in classpath)
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class AdminDashboard extends ComponentBase {
    private JTextField searchField;
    private JButton logoutButton;
    private JButton searchButton;
    private ChartPanel chartPanel;

    public AdminDashboard(MainWindow main_window) {
        super(main_window);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Top panel with logout button (styled)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(240, 245, 255));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger red
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton);

        // Middle panel with account ID search (styled)
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        userPanel.setOpaque(true);
        userPanel.setBackground(new Color(250, 250, 250));
        userPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        JLabel accountLabel = new JLabel("Account ID:");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userPanel.add(accountLabel);
        searchField = new JTextField(12);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(120, 28));
        userPanel.add(searchField);
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(0, 123, 255)); // Bootstrap primary blue
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        searchButton.addActionListener(e -> searchUser());
        userPanel.add(searchButton);

        // Line graph (JFreeChart) with border and anti-aliasing
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Example data (replace with real data)
        dataset.addValue(200, "Withdrawals", "2025-05-01");
        dataset.addValue(150, "Deposits", "2025-05-01");
        dataset.addValue(350, "All Transactions", "2025-05-01");
        dataset.addValue(100, "Withdrawals", "2025-05-02");
        dataset.addValue(300, "Deposits", "2025-05-02");
        dataset.addValue(400, "All Transactions", "2025-05-02");
        dataset.addValue(250, "Withdrawals", "2025-05-03");
        dataset.addValue(200, "Deposits", "2025-05-03");
        dataset.addValue(450, "All Transactions", "2025-05-03");
        JFreeChart lineChart = ChartFactory.createLineChart(
            "Daily Transaction Sums",
            "Date",
            "Amount",
            dataset
        );
        lineChart.setBackgroundPaint(Color.WHITE);
        lineChart.getPlot().setBackgroundPaint(new Color(245, 245, 255));
        lineChart.setAntiAlias(true);
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(600, 500));
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Transaction Trends"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        chartPanel.setBackground(Color.WHITE);

        // Use a panel with BoxLayout to control vertical sizing
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        chartPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        mainPanel.add(topPanel);
        mainPanel.add(userPanel);
        mainPanel.add(chartPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void logout() {
        main_window.gotoLogin();
    }

    private void searchUser() {
        String accountId = searchField.getText().trim();
        if (accountId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an account ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(accountId);
            // TODO: Implement user search logic
            JOptionPane.showMessageDialog(this, "Searched for account ID: " + id);
            main_window.gotoManageUserPage();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid account ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
// Note: Requires JFreeChart library (jfreechart and jcommon jars) in your classpath.