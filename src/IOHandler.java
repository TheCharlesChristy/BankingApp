package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class IOHandler {
    final private Scanner scanner;
    
    // Define log levels
    public enum LogLevel { DEBUG, INFO, WARNING, ERROR }
    // Configurable minimum log level
    private LogLevel minLevel = LogLevel.DEBUG;
    // Optional file logger
    private PrintWriter fileLogger = null;
    
    public IOHandler() {
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
    public String readLine() {
        return scanner.nextLine();
    }

    // Read an integer from the user with validation
    public int readInt() {
        while (true) {
            String input = readLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                println("Invalid input. Please enter a valid integer:");
            }
        }
    }
    
    // Read a double from the user with validation
    public double readDouble() {
        while (true) {
            String input = readLine();
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
    public void setMinLogLevel(LogLevel level) {
        minLevel = level;
    }
    
    // Configure file logging; pass null to disable file logging
    public void setLogFile(String filePath) {
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

    public String readFile(String string) {
        try (BufferedReader reader = new BufferedReader(new FileReader(string))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            info("File read successfully: " + string);
            return content.toString();
        } catch (IOException e) {
            error("Failed to read file: " + e.getMessage());
            return "";
        }
    }
}

