package errors;

public class IdAlreadyDeclaredError extends RuntimeException {
    public IdAlreadyDeclaredError(String id) {
        super("Identifier '" + id + "' already declared in the current scope!");
    }
}
