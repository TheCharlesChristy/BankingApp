package src.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import src.IOHandler;

public class DatabaseHandler {
    String url;
    IOHandler io;
    String[] sqls;

    public DatabaseHandler(String filePath) {
        this.io = new IOHandler();
        this.url = "jdbc:sqlite:" + filePath;
        this.sqls = new String[] {
            io.read_file("src/Assets/Database/users_table.sql"),
            io.read_file("src/Assets/Database/accounts_table.sql"),
            io.read_file("src/Assets/Database/transactions_table.sql"),
            io.read_file("src/Assets/Database/admins_table.sql"),
            io.read_file("src/Assets/Database/create_default_user.sql"),
            io.read_file("src/Assets/Database/add_admin.sql")
        };
    }

    public void execute_SQL(String sql) {
        // Execute a SQL statement
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute_SQL(String sql, Object... params) {
        // Execute a SQL statement with parameters
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Set the parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> get_execute_SQL(String sql, Object... params) {
        // Execute a SQL statement with a return value
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Set the parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            ResultSet output = statement.executeQuery();
            
            if (output.next()) {
                // Convert ResultSet to Map to avoid the "ResultSet closed" error
                Map<String, Object> row = new HashMap<>();
                int columnCount = output.getMetaData().getColumnCount();
                
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = output.getMetaData().getColumnName(i);
                    Object value = output.getObject(i);
                    row.put(columnName, value);
                }
                
                return row;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet get_raw_execute_SQL(String sql, Object... params) {
        // Execute a SQL statement with a return value
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Set the parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean check_database() {
        /*
         *  This method will perform an integrity check on the database.
         *  This is a simple but effective way of checking database integrity.
         */
        String integrity_sql = "PRAGMA integrity_check;";

        try (
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()
        ) {
            statement.execute(integrity_sql);
            ResultSet output = statement.getResultSet(); // Get the result set
            return output.getString("integrity_check").equals("ok");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void initialise_database() {
        /*
         * This method will check the database.
         * If the database is ok it will finish.
         * If the database is not ok it will log the error and attempt to restore from backup.
         * If the backup fails a integrity check then we will simply create a new database.
         */
        try {
            boolean check = check_database();

            io.info(check ? "Database integrity check passed. Database is ok." : "Database integrity check failed. Attempting to restore from backup.");

            if (check) {
                io.debug("Database integrity check passed. Database is ok.");
                return;
            }
        } catch (RuntimeException e) {
            // If the integrity check fails then we will attempt to restore from backup.
            io.error(e.getMessage());
            io.warning("Database integrity check failed. Attempting to restore from backup.");

            if (restore_from_backup()) {
                io.info("Database restored from backup.");
                return;
            } else {
                io.error("Failed to restore database from backup. Creating new database.");

                // Create a new database
                create_database();
                io.info("New database created.");
                create_backup("backup");
                io.info("Backup created.");
            }
        }
    }

    public void create_database() {
        // Create a new database
        io.info("Creating new database.");
        for (String sql : sqls) {
            try {
                execute_SQL(sql);
            } catch (Exception e) {
                io.debug("Ignoring exception while creating database: " + e.getMessage());
            }
        }
    }

    public void create_backup(String backup_name) {
        // Create a backup of the database
        String backup_sql = "VACUUM INTO '" + backup_name + ".db';";

        try (
            Connection connection = DriverManager.getConnection(url); // Get connection
            Statement statement = connection.createStatement() // Create a statement
        ) {
            statement.execute(backup_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean restore_from_backup() {
        // Restore the database from backup
        String restore_sql = "ATTACH DATABASE 'backup.db' AS backup;";

        try (
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()
        ) {
            // Attach the backup database
            // Then create a new database from the backup
            statement.execute(restore_sql);
            create_database();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void remove_database(String db_fname) {
        // Remove the database
        io.delete_file(db_fname);
    }
}