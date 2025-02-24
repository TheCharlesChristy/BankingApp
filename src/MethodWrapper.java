package src;

// Define a functional interface for wrapping methods
// This is useful for handling exceptions in methods
// <T> is the return type of the method and defines a generic type
@FunctionalInterface
public interface MethodWrapper<T> {
    T execute() throws Exception; // T = Return type, execute() = Method, throws Exception = Exception handling
}
