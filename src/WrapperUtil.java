package src;

public class WrapperUtil {
    public static <T> T wrap_method(MethodWrapper<T> method) {
        try {
            return method.execute();
        } catch (Exception e) {
            // Handle the exception as needed
            e.printStackTrace();
            return null;
        }
    }

    public static <T> boolean try_return_true_false(MethodWrapper<T> method, String method_name) {
        // Try to run a method and return true if successful, false otherwise
        // Print out the method name and whether it was successful
        try {
            method.execute();
            System.out.println("\u001B[32m" + method_name+ " SUCCESS" + "\u001B[0m");
            return true;
        } catch (Exception e) {
            System.out.println("\u001B[31m" + method_name + " FAILED" + "\u001B[0m");
            return false;
        }
    }
}
