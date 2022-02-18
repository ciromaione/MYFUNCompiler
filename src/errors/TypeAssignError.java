package errors;

import ast.Type;

public class TypeAssignError extends RuntimeException {
    public TypeAssignError(String id, Type varType, Type exprType) {
        super("Cannot assign '" + exprType.toString() + "' to the variable '" + id +"' of '" + varType.toString() + "' type!");
    }
}
