package errors;

import ast.Type;

public class InvalidTypeForBinaryOpError extends RuntimeException {
    public InvalidTypeForBinaryOpError(String op, Type op1, Type op2) {
        super("Impossible to perform an operation " + op + " between a '" + op1.toString() + "' and a '" + op2.toString() + "'!");
    }
}
