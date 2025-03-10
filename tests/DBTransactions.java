package tests;

import src.Database.DatabaseHandler;
import src.Structs.Transactions;
import src.Server.DataBaseInterface;
import src.WrapperUtil;
import java.time.LocalDateTime;

public class DBTransactions {
    DatabaseHandler db;
    DataBaseInterface db_interface;
    Transactions ref_transaction;
    
    public DBTransactions() {
        db = new DatabaseHandler("test.db");
        db_interface = new DataBaseInterface(db);
        ref_transaction = new Transactions(1, 1, 100.0f, "deposit", LocalDateTime.now());
    }
    
    public boolean do_tests() {
        db.create_database();
        db.initialise_database();
        boolean result = test_create_transaction();
        result = result && test_get_transaction();
        result = result && test_update_transaction();
        result = result && test_delete_transaction();
        db.remove_database("test.db");
        return result;
    }
    
    public boolean test_create_transaction() {
        return WrapperUtil.try_return_true_false(() -> {
            db_interface.transactions_interface.create_transaction(ref_transaction);
            return null;
        }, "create_transaction");
    }
    
    public boolean test_get_transaction() {
        return WrapperUtil.try_return_true_false(() -> {
            Transactions test_trans = db_interface.transactions_interface.get_transaction(1);
            if (test_trans.id == ref_transaction.id && 
                test_trans.account_id == ref_transaction.account_id &&
                test_trans.amount == ref_transaction.amount &&
                test_trans.type.equals(ref_transaction.type)) {
                return null;
            }
            else {
                return new Exception("Transaction does not match");
            }
        }, "get_transaction");
    }
    
    public boolean test_update_transaction() {
        return WrapperUtil.try_return_true_false(() -> {
            ref_transaction.amount = 200.0f;
            ref_transaction.type = "withdrawal";
            db_interface.transactions_interface.update_transaction(ref_transaction);
            Transactions test_trans = db_interface.transactions_interface.get_transaction(1);
            if (test_trans.id == ref_transaction.id && 
                test_trans.account_id == ref_transaction.account_id &&
                test_trans.amount == ref_transaction.amount &&
                test_trans.type.equals(ref_transaction.type)) {
                return null;
            }
            else {
                return new Exception("Transaction does not match after update");
            }
        }, "update_transaction");
    }
    
    public boolean test_delete_transaction() {
        return WrapperUtil.try_return_true_false(() -> {
            db_interface.transactions_interface.delete_transaction(1);
            if (!db_interface.transactions_interface.check_exists(1)) {
                return null;
            }
            else {
                return new Exception("Transaction still exists after deletion");
            }
        }, "delete_transaction");
    }
    
    public static void main(String[] args) {
        DBTransactions db_transaction = new DBTransactions();
        boolean success = db_transaction.do_tests();
        System.out.println("Tests " + (success ? "passed" : "failed"));
    }
}
