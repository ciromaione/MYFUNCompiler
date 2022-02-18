package errors;

public class VariableNotDeclaredError extends RuntimeException {
    public VariableNotDeclaredError(String id) {
        super("Variable '" + id + "' used but not declared!");
    }
}
