package src.Server.Interfaces;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Database.DatabaseHandler;
import src.Structs.Transactions;

public class TransactionsInterface {
    DatabaseHandler db;

    public TransactionsInterface(DatabaseHandler db) {
        this.db = db;
    }

    public void create_transaction(Transactions transaction) {
        String sql = "INSERT INTO Transactions (account_id, amount, type, created_at) VALUES (?, ?, ?, ?)";
        db.execute_SQL(sql, transaction.account_id, transaction.amount, transaction.type, transaction.created_at);
    }

    public boolean check_exists(int transaction_id) {
        String sql = "SELECT * FROM Transactions WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, transaction_id);
        return result != null;
    }

    public Transactions get_transaction(int transaction_id) {
        String sql = "SELECT * FROM Transactions WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, transaction_id);
        
        // if the object is null, the transaction does not exist
        if (result == null) {
            System.out.println("Transaction does not exist");
            return null;
        }
        
        // if the object is not null, the transaction exists
        try {
            return parse_transaction(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transactions> get_transactions(int account_id) {
        String sql = "SELECT * FROM Transactions WHERE account_id = ?";
        ResultSet results = db.get_raw_execute_SQL(sql, account_id);
        List<Transactions> transactions = new ArrayList<Transactions>();
        
        try {
            while (results.next()) {
                Map<String, Object> result = parse_result_set(results);
                transactions.add(parse_transaction(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return transactions;
    }

    private Map<String, Object> parse_result_set(ResultSet results) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", results.getInt("id"));
            result.put("account_id", results.getInt("account_id"));
            result.put("amount", results.getFloat("amount"));
            result.put("type", results.getString("type"));
            result.put("created_at", results.getString("created_at"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Transactions parse_transaction(Map<String, Object> result) {
        int id = ((Number) result.get("id")).intValue();
        int account_id = ((Number) result.get("account_id")).intValue();
        float amount = ((Number) result.get("amount")).floatValue();
        String type = (String) result.get("type");
        String created_at_str = (String) result.get("created_at");
        
        LocalDateTime created_at;
        try {
            // First try with the original formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            created_at = LocalDateTime.parse(created_at_str, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            // If that fails, try parsing as ISO format (which is the default)
            created_at = LocalDateTime.parse(created_at_str);
        }

        return new Transactions(id, account_id, amount, type, created_at);
    }

    public void update_transaction(Transactions transaction) {
        String sql = "UPDATE Transactions SET amount = ?, type = ? WHERE id = ?";
        db.execute_SQL(sql, transaction.amount, transaction.type, transaction.id);
    }

    public void delete_transaction(int transaction_id) {
        String sql = "DELETE FROM Transactions WHERE id = ?";
        db.execute_SQL(sql, transaction_id);
    }
}
