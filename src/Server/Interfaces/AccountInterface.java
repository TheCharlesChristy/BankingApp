package src.Server.Interfaces;

import src.Database.DatabaseHandler;
import src.Structs.User;

public class AccountInterface {
    DatabaseHandler db;

    public AccountInterface() {
        db = new DatabaseHandler("banking.db");
    }

    public AccountInterface(DatabaseHandler db) {
        this.db = db;
    }

    public void create_account(User user) {
        // Create a new account
        String sql = "INSERT INTO users (username, password, balance, created_at) VALUES (?, ?, ?, ?);";
        this.db.execute_SQL(sql, user.username, user.password, user.balance, user.created_at);
    }

    public Object get_account(String username) {
        // Get an account
        String sql = "SELECT * FROM users WHERE username = ?;";
        return this.db.execute_SQL(sql, username);
    }

    public void update_account(String target_username, User user) {
        // Update an account
        String sql = "UPDATE users SET username = ?, password = ?, balance = ?, created_at = ? WHERE username = ?;";
        this.db.execute_SQL(sql, user.username, user.password, user.balance, user.created_at, target_username);
    }

    public void delete_account(String username) {
        // Delete an account
        String sql = "DELETE FROM users WHERE username = ?;";
        this.db.execute_SQL(sql, username);
    }
}
