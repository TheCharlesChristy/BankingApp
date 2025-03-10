package tests;

import src.Database.DatabaseHandler;
import src.Structs.Users;
import src.Server.DataBaseInterface;
import src.WrapperUtil;

public class DBUsers {
    DatabaseHandler db;
    DataBaseInterface db_interface;
    Users ref_user;

    public DBUsers() {
        db = new DatabaseHandler("test.db");
        db_interface = new DataBaseInterface(db);
        ref_user = new Users(1, "test_user", "password", "test@gmail.com", 1234);
    }

    public boolean do_tests() {
        db.create_database();
        db.initialise_database();
        boolean result = test_create_user();
        result = result && test_get_user();
        result = result && test_update_user(); 
        db.remove_database("test.db");
        return result;
    }

    public boolean test_create_user() {
        return WrapperUtil.try_return_true_false(() -> {
            db_interface.user_interface.create_user(ref_user);
            return null;
        }, "create_user");
    }

    public boolean test_get_user() {
        return WrapperUtil.try_return_true_false(() -> {
            Users test_acc = db_interface.user_interface.get_user(1);
            if (test_acc.equals(ref_user)) {
                return null;
            }
            else {
                return new Exception("User does not match");
            }
        }, "get_user");
    }

    public boolean test_update_user() {
        return WrapperUtil.try_return_true_false(() -> {
            ref_user.username = "test_change";
            db_interface.user_interface.update_user(ref_user);
            Users test_user = db_interface.user_interface.get_user(1);
            if (test_user.equals(ref_user)) {
                return null;
            }
            else {
                return new Exception("User does not match");
            }
        }, "update_user");
    }

    public static void main(String[] args) {
        DBUsers db_user = new DBUsers();
        db_user.do_tests();
    }
}
