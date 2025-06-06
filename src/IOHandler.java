package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IOHandler {
    private Scanner scanner;
    
    // Define log levels
    public enum LogLevel { DEBUG, INFO, WARNING, ERROR }
    // Configurable minimum log level
    private LogLevel minLevel = LogLevel.DEBUG;
    // Optional file logger
    private PrintWriter fileLogger = null;
    
    public IOHandler() {
        scanner = new Scanner(System.in);
    }

    private void reset_scanner() {
        scanner.reset();
        scanner = new Scanner(System.in);
    }

    // Print a message without a new line
    public void print(String message) {
        System.out.print(message);
    }

    // Print a message with a new line
    public void println(String message) {
        System.out.println(message);
    }

    // Read a full line of input
    public String read_line() {
        Console console = System.console();
        if (console != null) {
            // This properly handles the console mode for password input
            String inp = console.readLine();
            // Make sure to print a newline after password input
            System.out.println();
            reset_scanner();
            console.flush();
            return inp;
        } else {
            // Fallback for environments without console (like some IDEs)
            reset_scanner();
            return scanner.nextLine();
        }
    }

    public String read_line(String prompt) {
        Console console = System.console();
        if (console != null) {
            // This properly handles the console mode for password input
            String inp = console.readLine(prompt);
            // Make sure to print a newline after password input
            System.out.println();
            reset_scanner();
            console.flush();
            return inp;
        } else {
            // Fallback for environments without console (like some IDEs)
            reset_scanner();
            print(prompt);
            return scanner.nextLine();
        }
    }

    public String read_pass(String prompt) {
        Console console = System.console();
        if (console != null) {
            // This properly handles the console mode for password input
            char[] passwordChars = console.readPassword(prompt);
            String password = new String(passwordChars);
            // Clear the password array for security
            java.util.Arrays.fill(passwordChars, ' ');
            // Make sure to print a newline after password input
            System.out.println();
            reset_scanner();
            return password;
        } else {
            // Fallback for environments without console (like some IDEs)
            System.out.print(prompt);
            reset_scanner();
            return scanner.nextLine();
        }
    }

    public String read_pass() {
        Console console = System.console();
        if (console != null) {
            // This properly handles the console mode for password input
            char[] passwordChars = console.readPassword();
            String password = new String(passwordChars);
            // Clear the password array for security
            java.util.Arrays.fill(passwordChars, ' ');
            // Make sure to print a newline after password input
            System.out.println();
            reset_scanner();
            console.flush();
            return password;
        } else {
            // Fallback for environments without console (like some IDEs)
            reset_scanner();
            return scanner.nextLine();
        }
    }

    public String prompt(String message) {
        return read_line(message);
    }

    public int prompt_int(String message) {
        return read_int(message);
    }

    public int prompt_int_no_loop(String message) {
        return read_int_no_loop(message);
    }

    public String prompt_password(String message) {
        String pswd = read_pass(message);
        return hash_password(pswd);
    }

    public String hash_password(String password) {
        return Integer.toString(password.hashCode());
    }

    // Read an integer from the user with validation
    public int read_int(String prompt) {
        while (true) {
            String input = read_line(prompt);
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                println("Invalid input. Please enter a valid integer:");
            }
        }
    }

    public int read_int() {
        while (true) {
            String input = read_line();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                println("Invalid input. Please enter a valid integer:");
            }
        }
    }


    public int read_int_no_loop(String prompt) {
        String input = read_line(prompt);
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int read_int_no_loop() {
        String input = read_line();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // Read a double from the user with validation
    public double read_double() {
        while (true) {
            String input = read_line();
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                println("Invalid input. Please enter a valid number:");
            }
        }
    }

    public double read_double(String prompt) {
        while (true) {
            String input = read_line(prompt);
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                println("Invalid input. Please enter a valid number:");
            }
        }
    }

    // Cleanup resources if needed
    public void close() {
        scanner.close();
    }

    // Set the minimum log level
    public void set_min_log_level(LogLevel level) {
        minLevel = level;
    }
    
    // Configure file logging; pass null to disable file logging
    public void set_log_file(String filePath) {
        if(filePath == null) {
            if(fileLogger != null) {
                fileLogger.close();
            }
            fileLogger = null;
            return;
        }
        try {
            fileLogger = new PrintWriter(new FileWriter(filePath, true));
        } catch(IOException e) {
            System.err.println("Failed to set log file: " + e.getMessage());
        }
    }
    
    // Private helper to log messages with timestamps and levels
    private void log(LogLevel level, String message) {
        if(level.ordinal() < minLevel.ordinal()) {
            return;
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = String.format("[%s] [%s]: %s", timestamp, level, message);
        System.out.println(logMessage);
        if(fileLogger != null) {
            fileLogger.println(logMessage);
            fileLogger.flush();
        }
    }

    // Log a debug message
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    // Log an info message
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    // Log a warning message
    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    // Log an error message
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void write_file(String fname, String content) {
        try (PrintWriter writer = new PrintWriter(fname)) {
            writer.print(content);
            info("File written successfully: " + fname);
        } catch (IOException e) {
            error("Failed to write file: " + e.getMessage());
        }
    }

    public String read_file(String fname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            info("File read successfully: " + fname);
            return content.toString();
        } catch (IOException e) {
            error("Failed to read file: " + e.getMessage());
            return "";
        }
    }

    public void delete_file(String fname) {
        if (new java.io.File(fname).delete()) {
            info("File deleted successfully: " + fname);
        } else {
            error("Failed to delete file: " + fname);
        }
    }

    public JSONObject read_json(String fname) throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(read_file(fname));
            return (JSONObject) obj;
        } catch (ParseException e) {
            error("Failed to read JSON file: " + e.getMessage());
            return new JSONObject();
        }
    }

    public void write_json(String fname, JSONObject obj) {
        write_file(fname, obj.toJSONString());
    }

    public int get_random_int(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}

