package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
    String url;

    public DatabaseHandler(String filePath) {
        this.url = "jdbc:sqlite:" + filePath;
    }

    public void executeSQL(String sql) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void check_table() {
        String integrity_sql = "PRAGMA integrity_check;";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            statement.execute(integrity_sql);
            ResultSet output = statement.getResultSet();
            while (output.next()) {
                System.out.println(output.getString("integrity_check"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}