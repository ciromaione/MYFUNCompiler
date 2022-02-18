package errors;

import ast.Type;

public class ConditionNotBoolError extends RuntimeException {
    public ConditionNotBoolError(String istrName, Type type) {
        super("The condition in the " + istrName + " statement is '" + type.toString() + "', must be 'bool'!");
    }
}
