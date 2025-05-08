package src.Server.Interfaces;

import java.util.Map;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import src.Database.DatabaseHandler;
import src.Structs.Admins;

public class AdminsInterface {
    DatabaseHandler db;
    
    public AdminsInterface(DatabaseHandler db) {
        this.db = db;
    }
    
    public void create_admin(String username) {
        String sql = "INSERT INTO Admins (username) VALUES (?)";
        db.execute_SQL(sql, username);
    }
    
    public boolean is_admin(String username) {
        String sql = "SELECT * FROM Admins WHERE username = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, username);
        return result != null;
    }
    
    public boolean is_admin(int admin_id) {
        String sql = "SELECT * FROM Admins WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, admin_id);
        return result != null;
    }
    
    public Map<String, Object> get_admin(String username) {
        String sql = "SELECT * FROM Admins WHERE username = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, username);
        
        // if the object is null, the admin does not exist
        if (result == null) {
            System.out.println("Admin does not exist");
            return null;
        }
        
        return result;
    }
    
    public Map<String, Object> get_admin(int admin_id) {
        String sql = "SELECT * FROM Admins WHERE id = ?";
        Map<String, Object> result = db.get_execute_SQL(sql, admin_id);
        
        // if the object is null, the admin does not exist
        if (result == null) {
            System.out.println("Admin does not exist");
            return null;
        }
        
        return result;
    }
    
    public List<Admins> get_all_admins(UserInterface userInterface) {
        String sql = "SELECT * FROM Admins";
        List<Map<String, Object>> results = db.get_many_execute_SQL(sql);
        System.out.println("Results from get_all_admins query: " + results);

        List<Admins> admins = new ArrayList<>();
        try {
            if (results == null) {
                System.out.println("ResultSet is null for all admins");
                return admins;
            }

            for (Map<String, Object> result : results) {
                admins.add(parse_admin(result));
            }
            
            System.out.println("Found " + admins.size() + " admins");
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return admins;
    }

    public Admins parse_admin(Map<String, Object> result) {
        String username = (String) result.get("username");
        int id = (int) result.get("id");
        return new Admins(id, username);
    }
    
    public void delete_admin(String username) {
        String sql = "DELETE FROM Admins WHERE username = ?";
        db.execute_SQL(sql, username);
    }
    
    public void delete_admin(int admin_id) {
        String sql = "DELETE FROM Admins WHERE id = ?";
        db.execute_SQL(sql, admin_id);
    }
}
