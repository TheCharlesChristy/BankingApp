package src.Server.Interfaces;

import java.util.Map;

import src.Database.DatabaseHandler;
import src.Structs.Accounts;

public class AccountInterface {
    DatabaseHandler db;

    public AccountInterface(DatabaseHandler db) {
        this.db = db;
    }

    public void create_account(Accounts account) {
        String sql = "INSERT INTO Accounts (user_id, balance, interest_rate, created_at) VALUES (?, ?, ?, ?)";
        db.execute_SQL(sql, account.user_id, account.balance, account.interest_rate, account.created_at);
    }

    public boolean check_exists(int account_id) {
        String sql = "SELECT * FROM Accounts WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, account_id);
        return result != null;
    }

    public Accounts get_account(int account_id) {
        String sql = "SELECT * FROM Accounts WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, account_id);
        
        // if the object is null, the account does not exist
        if (result == null) {
            System.out.println("Account does not exist");
            return null;
        }
        
        // if the object is not null, the account exists
        try {
            return parse_account(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Accounts parse_account(Map<String, Object> result) {
        int id = ((Number) result.get("id")).intValue();
        int user_id = ((Number) result.get("user_id")).intValue();
        float balance = ((Number) result.get("balance")).floatValue();
        float interest_rate = ((Number) result.get("interest_rate")).floatValue();
        return new Accounts(id, user_id, balance, interest_rate);
    }

    public void update_account(Accounts account) {
        String sql = "UPDATE Accounts SET balance = ?, interest_rate = ? WHERE id = ?";
        db.execute_SQL(sql, account.balance, account.interest_rate, account.id);
    }

    public void delete_account(int account_id) {
        String sql = "DELETE FROM Accounts WHERE id = ?";
        db.execute_SQL(sql, account_id);
    }
}
