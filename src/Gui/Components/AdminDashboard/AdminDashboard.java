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

        // Top panel with logout button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton);

        // Middle panel with account ID search
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        userPanel.setOpaque(false);
        searchField = new JTextField(10);
        userPanel.add(new JLabel("Account ID:"));
        userPanel.add(searchField);
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(e -> searchUser());
        userPanel.add(searchButton);

        // Line graph (JFreeChart) at the bottom, but using a vertical BoxLayout for full height
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
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(600, 400));

        // Use a panel with BoxLayout to control vertical sizing
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        chartPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        mainPanel.add(topPanel);
        mainPanel.add(userPanel);
        mainPanel.add(chartPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void logout() {
        System.out.println("Logging out");
        // You can add navigation logic here
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
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid account ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
// Note: Requires JFreeChart library (jfreechart and jcommon jars) in your classpath.