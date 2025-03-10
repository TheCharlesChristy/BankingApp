package src.Server.Interfaces;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import src.Database.DatabaseHandler;
import src.Structs.Users;

public class UserInterface {
    DatabaseHandler db;

    public UserInterface(DatabaseHandler db) {
        this.db = db;
    }

    public void create_user(Users user) {
        String sql = "INSERT INTO Users (username, password, email, pin) VALUES (?, ?, ?, ?)";
        db.execute_SQL(sql, user.get_username(), user.get_password(), user.get_email(), user.get_pin());
    }

    public boolean check_exists(int user_id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, user_id);
        return result != null;
    }

    public boolean check_exists(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, username);
        return result != null;
    }

    public Users get_user(int user_id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, user_id);
        
        // if the object is null, the user does not exist
        if (result == null) {
            System.out.println("user does not exist");
            return null;
        }
        
        // if the object is not null, the user exists
        try {
            return parse_user(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Users get_user(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, username);
        
        // if the object is null, the user does not exist
        if (result == null) {
            System.out.println("user does not exist");
            return null;
        }
        
        // if the object is not null, the user exists
        try {
            return parse_user(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Users parse_user(Map<String, Object> result) {
        int id = ((Number) result.get("id")).intValue();
        String username = (String) result.get("username");
        String password = (String) result.get("password");
        String email = (String) result.get("email");
        int pin = ((Number) result.get("pin")).intValue();
        String created_at_str = (String) result.get("created_at");
        
        // Define a formatter that can parse the database datetime format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime created_at = LocalDateTime.parse(created_at_str, formatter);
        
        return new Users(id, username, password, email, pin, created_at);
    }

    public void update_user(Users user) {
        String sql = "UPDATE Users SET username = ?, password = ?, email = ?, pin = ? WHERE id = ?";
        db.execute_SQL(sql, user.get_username(), user.get_password(), user.get_email(), user.get_pin(), user.get_id());
    }

    public void delete_user(int user_id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        db.execute_SQL(sql, user_id);
    }

    public void delete_user(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";
        db.execute_SQL(sql, username);
    }
}
