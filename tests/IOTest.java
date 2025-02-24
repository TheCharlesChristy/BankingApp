package tests;

import src.IOHandler;
import src.WrapperUtil;
import java.io.File;

public class IOTest {
    IOHandler io;

    public IOTest() {
        io = new IOHandler();
    }

    public boolean do_tests() {
        return test_print() && test_file_rw();
    }

    private boolean test_print() {
        return WrapperUtil.try_return_true_false(() -> {
            io.println("Hello, World!");
            return null;
        }, "test_print");
    }

    private boolean test_file_rw() {
        return WrapperUtil.try_return_true_false(() -> {
            // Create a file with some content
            String test_fname = "test.txt";
            String test_content = "Hello, World!";
            io.write_file(test_fname, test_content);

            // Read the file
            String fileContent = io.read_file(test_fname);
            fileContent = fileContent.strip();

            if (!fileContent.equals(test_content)) {
                throw new Exception("File content mismatch");
            }

            // Cleanup
            new File(test_fname).delete();
            return null;
        }, "test_file_rw");
    }
}
