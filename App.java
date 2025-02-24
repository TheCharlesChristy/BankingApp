import src.IOHandler;
import src.DatabaseHandler;

public class App {
    public static void main(String[] args) {
        IOHandler io = new IOHandler();
        DatabaseHandler db = new DatabaseHandler("banking.db");
        // Read the SQL file
        String[] sqls = {
            io.readFile("src/Assets/Database/users_table.sql"),
            io.readFile("src/Assets/Database/accounts_table.sql"),
            io.readFile("src/Assets/Database/transactions_table.sql"),
            io.readFile("src/Assets/Database/admins_table.sql")
        };

        io.setLogFile("app_log.txt");
        io.setMinLogLevel(IOHandler.LogLevel.INFO);
        io.info("Checking Database Integrity...");
        for (String sql : sqls) {
            db.executeSQL(sql);
        }
        io.println("Welcome to the Banking App!");
    }
}
