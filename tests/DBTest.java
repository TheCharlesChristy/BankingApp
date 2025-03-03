package tests;

import src.Database.DatabaseHandler;
import src.WrapperUtil;


public class DBTest {
    DatabaseHandler db;

    public DBTest() {
        db = new DatabaseHandler("test.db");
    }

    public boolean do_tests() {
        return 
        test_create_db() 
        && test_check_db()
        && test_backup_db() 
        && test_restore_db() 
        && test_exec_sql() 
        && remove_test_db();
    }

    public boolean test_create_db() {
        return WrapperUtil.try_return_true_false(() -> {
            db.create_database();
            return null;
        }, "test_create_db");
    }

    public boolean test_check_db() {
        return WrapperUtil.try_return_true_false(() -> {
            db.check_database();
            return null;
        }, "test_check_db");
    }

    public boolean test_backup_db() {
        return WrapperUtil.try_return_true_false(() -> {
            db.create_backup("test_backup_db");
            return null;
        }, "test_backup_db");
    }

    public boolean test_restore_db() {
        return WrapperUtil.try_return_true_false(() -> {
            db.restore_from_backup();
            return null;
        }, "test_restore_db");
    }

    public boolean test_exec_sql() {
        return WrapperUtil.try_return_true_false(() -> {
            db.execute_SQL("CREATE TABLE test_table (id INTEGER PRIMARY KEY, name TEXT);");
            return null;
        }, "test_exec_sql");
    }

    public boolean remove_test_db() {
        return WrapperUtil.try_return_true_false(() -> {
            db.remove_database("test.db");
            db.remove_database("test_backup_db.db");
            return null;
        }, "remove_test_db");
    }
}
