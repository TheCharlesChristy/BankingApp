package tests;

import src.IOHandler;
import src.WrapperUtil;
import java.io.File;

import org.json.simple.JSONObject;

public class IOTest {
    IOHandler io;

    public IOTest() {
        io = new IOHandler();
    }

    public boolean do_tests() {
        return test_print() && test_file_rw() && test_json_rw();
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

    private boolean test_json_rw() {
        return WrapperUtil.try_return_true_false(() -> {
            // Create a JSON file with some content
            String test_fname = "test.json";
            String test_content = "{\"key\":\"value\"}";
            io.write_file(test_fname, test_content);

            // Read the JSON file
            JSONObject json = io.read_json(test_fname);
            String jsonContent = json.toString();
            jsonContent = jsonContent.strip();

            if (!jsonContent.equals(test_content)) {
                throw new Exception("JSON content mismatch");
            }

            // Cleanup
            new File(test_fname).delete();
            return null;
        }, "test_json_rw");
    }
}
