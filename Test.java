import tests.IOTest;
import tests.DBTest;
import tests.DBAccount;

public class Test {
    IOTest io_test;
    DBTest db_test;
    DBAccount db_account;

    public Test() {
        io_test = new IOTest();
        db_test = new DBTest();
        db_account = new DBAccount();
    }

    public boolean do_tests() {
        print_info("Running tests...");
        print_info("Running IO tests...");
        io_test();
        print_info("Running Database tests...");
        db_test();
        print_info("Running Account tests...");
        db_account_test();
        return true;
    }

    public boolean do_io_tests() {
        return io_test.do_tests();
    }

    public void throw_exception(String msg) {
        throw new RuntimeException(msg);
    }

    public void print_success(String msg) {
        System.out.println("\u001B[32m" + msg + "\u001B[0m"); // Green text
    }

    public void print_failure(String msg) {
        System.out.println("\u001B[31m" + msg + "\u001B[0m"); // Red text
    }

    public void print_info(String msg) {
        System.out.println("\u001B[34m" + msg + "\u001B[0m"); // Blue text
    }

    public void io_test() {
        if (do_io_tests()) {
            print_success("IO tests passed.");
        } else {
            print_failure("IO tests failed.");
            // Throw an exception
            throw_exception("IO tests failed.");
        }
    }

    public void db_test() {
        if (do_db_tests()) {
            print_success("Database tests passed.");
        } else {
            print_failure("Database tests failed.");
            // Throw an exception
            throw_exception("Database tests failed.");
        }
    }


    public boolean do_db_tests() {
        return db_test.do_tests();
    }

    public void db_account_test() {
        if (do_account_tests()) {
            print_success("Account tests passed.");
        } else {
            print_failure("Account tests failed.");
            // Throw an exception
            throw_exception("Account tests failed.");
        }
    }

    public boolean do_account_tests() {
        return db_account.do_tests();
    }

    public static void main(String[] args) {
        Test test = new Test();

        if (test.do_tests()) {
            test.print_success("All tests passed.");
        } else {
            test.print_failure("Some tests failed.");
        }
    }
}
