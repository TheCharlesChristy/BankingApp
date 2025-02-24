import tests.IOTest;

public class Test {
    IOTest io_test;

    public Test() {
        io_test = new IOTest();
    }

    public boolean do_tests() {
        return io_test.do_tests();
    }

    public static void main(String[] args) {
        Test test = new Test();
        if (test.do_tests()) {
            System.out.println("All tests passed.");
        } else {
            System.out.println("Some tests failed.");
        }
    }
}
