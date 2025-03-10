package tests;

import src.Database.DatabaseHandler;
import src.Structs.Admins;
import src.Server.DataBaseInterface;
import src.WrapperUtil;

import java.util.List;
import java.util.Map;

public class DBAdmins {
    DatabaseHandler db;
    DataBaseInterface db_interface;
    String ref_username = "testadmin";
    
    public DBAdmins() {
        db = new DatabaseHandler("test.db");
        db_interface = new DataBaseInterface(db);
    }
    
    public boolean do_tests() {
        db.create_database();
        db.initialise_database();
        boolean result = test_create_admin();
        result = result && test_is_admin_username();
        result = result && test_get_admin_username();
        result = result && test_get_all_admins();
        result = result && test_delete_admin();
        db.remove_database("test.db");
        return result;
    }
    
    public boolean test_create_admin() {
        return WrapperUtil.try_return_true_false(() -> {
            db_interface.admins_interface.create_admin(ref_username);
            return null;
        }, "create_admin");
    }
    
    public boolean test_is_admin_username() {
        return WrapperUtil.try_return_true_false(() -> {
            boolean isAdmin = db_interface.admins_interface.is_admin(ref_username);
            if (isAdmin) {
                return null;
            } else {
                return new Exception("Admin should exist but was not found");
            }
        }, "is_admin_username");
    }
    
    public boolean test_get_admin_username() {
        return WrapperUtil.try_return_true_false(() -> {
            Map<String, Object> admin = db_interface.admins_interface.get_admin(ref_username);
            if (admin != null && admin.get("username").equals(ref_username)) {
                return null;
            } else {
                return new Exception("Admin does not match or was not found");
            }
        }, "get_admin_username");
    }
    
    public boolean test_get_all_admins() {
        return WrapperUtil.try_return_true_false(() -> {
            List<Admins> admins = db_interface.admins_interface.get_all_admins(db_interface.user_interface);
            if (admins.size() > 0) {
                return null;
            } else {
                return new Exception("No admins found");
            }
        }, "get_all_admins");
    }
    
    public boolean test_delete_admin() {
        return WrapperUtil.try_return_true_false(() -> {
            db_interface.admins_interface.delete_admin(ref_username);
            boolean isAdmin = db_interface.admins_interface.is_admin(ref_username);
            if (!isAdmin) {
                return null;
            } else {
                return new Exception("Admin should have been deleted");
            }
        }, "delete_admin");
    }
    
    public static void main(String[] args) {
        DBAdmins db_admins = new DBAdmins();
        boolean result = db_admins.do_tests();
        System.out.println("Tests " + (result ? "passed" : "failed"));
    }
}
