package src.Gui.Components.UserDashboard;

import src.Gui.MainWindow;
import src.Gui.ComponentBase;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

public class AccountTransaction extends ComponentBase {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterDropdown;
    private String[] columns = {"Amount", "Type", "Date"};
    private String[][] data = {
        {"+500.00", "Deposit", "2025-05-01"},
        {"-100.00", "Withdrawal", "2025-05-02"},
        {"-50.00", "Transfer", "2025-05-03"}
    };

    public AccountTransaction(MainWindow window) {
        super(window);
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Transactions", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        filterDropdown = new JComboBox<>(new String[]{"All", "Deposit", "Withdrawal"});
        filterDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(filterDropdown, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTable.setRowHeight(24);
        transactionTable.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        filterDropdown.addActionListener(e -> on_transaction_type_changed());
    }

    private void filterTransactions() {
        String filter = (String) filterDropdown.getSelectedItem();
        // TODO: Implement actual filtering from data source
        // For now, just a placeholder
        tableModel.setRowCount(0);
        for (String[] row : data) {
            if (filter.equals("All") || row[1].equals(filter)) {
                tableModel.addRow(row);
            }
        }
    }

    private void on_transaction_type_changed() {
        System.out.println("Transaction type changed to: " + filterDropdown.getSelectedItem());
        filterTransactions();
    }
}
