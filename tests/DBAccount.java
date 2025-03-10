package tests;

import src.Database.DatabaseHandler;
import src.Structs.Accounts;
import src.Server.DataBaseInterface;
import src.WrapperUtil;

public class DBAccount {
    DatabaseHandler db;
    DataBaseInterface db_interface;
    Accounts ref_account;

    public DBAccount() {
        db = new DatabaseHandler("test.db");
        db_interface = new DataBaseInterface(db);
        ref_account = new Accounts(1, 1, 10.0f, 0.1f);
    }

    public boolean do_tests() {
        db.create_database();
        db.initialise_database();
        boolean result = test_create_account();
        result = result && test_get_account();
        result = result && test_update_account(); 
        db.remove_database("test.db");
        return result;
    }

    public boolean test_create_account() {
        return WrapperUtil.try_return_true_false(() -> {
            db_interface.account_interface.create_account(ref_account);
            return null;
        }, "create_account");
    }

    public boolean test_get_account() {
        return WrapperUtil.try_return_true_false(() -> {
            Accounts test_acc = db_interface.account_interface.get_account(1);
            if (test_acc.equals(ref_account)) {
                return null;
            }
            else {
                return new Exception("Account does not match");
            }
        }, "get_account");
    }

    public boolean test_update_account() {
        return WrapperUtil.try_return_true_false(() -> {
            ref_account.balance = 20.0f;
            db_interface.account_interface.update_account(ref_account);
            Accounts test_acc = db_interface.account_interface.get_account(1);
            if (test_acc.equals(ref_account)) {
                return null;
            }
            else {
                return new Exception("Account does not match");
            }
        }, "update_account");
    }

    public static void main(String[] args) {
        DBAccount db_account = new DBAccount();
        db_account.do_tests();
    }
}
