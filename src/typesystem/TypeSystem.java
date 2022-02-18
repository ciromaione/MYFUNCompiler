package typesystem;

import ast.Type;
import ast.expr.BinaryOpNode;
import ast.expr.NotOp;
import ast.expr.UminusOp;

public class TypeSystem {

    public static Type getTypeForUminusOp(UminusOp op) {
        if(op.right.getNodeType() == Type.INTEGER) return Type.INTEGER;
        if(op.right.getNodeType() == Type.REAL) return Type.REAL;
        return null;
    }

    public static Type getTypeForNotOp(NotOp op) {
        if(op.right.getNodeType() == Type.BOOL) return Type.BOOL;
        return null;
    }

    public static Type getTypeFromBinaryOp(BinaryOpNode binaryOpNode) {
        switch(binaryOpNode.name) {
            case "AddOp", "DiffOp", "MulOp", "DivOp", "PowOp":
                return getTypeFromClassicNumericOps(binaryOpNode);
            case "DivIntOp":
                return getDivIntType(binaryOpNode);
            case "AndOp", "OrOp":
                return getTypeFromClassicBooleanOps(binaryOpNode);
            case "EQOp", "GEOp", "GTOp", "LEOp", "LTOp", "NEOp":
                return getTypeFromLogicOps(binaryOpNode);
            case "StrCatOp":
                if(binaryOpNode.left.getNodeType() != null && binaryOpNode.right.getNodeType() != null)
                    return Type.STRING;
        }
        return null;
    }

    private static Type getTypeFromLogicOps(BinaryOpNode binaryOpNode) {
        Type op1type = binaryOpNode.left.getNodeType(), op2type = binaryOpNode.right.getNodeType();
        if(op1type == Type.INTEGER && op2type == Type.INTEGER) return Type.BOOL;
        if(op1type == Type.INTEGER && op2type == Type.REAL) return Type.BOOL;
        if(op1type == Type.REAL && op2type == Type.INTEGER) return Type.BOOL;
        if(op1type == Type.REAL && op2type == Type.REAL) return Type.BOOL;
        if(op1type == Type.STRING && op2type == Type.STRING) return Type.BOOL;
        return null;
    }

    private static Type getTypeFromClassicBooleanOps(BinaryOpNode binaryOpNode) {
        Type op1type = binaryOpNode.left.getNodeType(), op2type = binaryOpNode.right.getNodeType();
        if(op1type == Type.BOOL && op2type == Type.BOOL) return Type.BOOL;
        return null;
    }

    private static Type getDivIntType(BinaryOpNode binaryOpNode) {
        Type op1type = binaryOpNode.left.getNodeType(), op2type = binaryOpNode.right.getNodeType();
        if(op1type == Type.INTEGER && op2type == Type.INTEGER) return Type.INTEGER;
        if(op1type == Type.INTEGER && op2type == Type.REAL) return Type.INTEGER;
        if(op1type == Type.REAL && op2type == Type.INTEGER) return Type.INTEGER;
        if(op1type == Type.REAL && op2type == Type.REAL) return Type.INTEGER;
        return null;
    }

    private static Type getTypeFromClassicNumericOps(BinaryOpNode binaryOpNode) {
        Type op1type = binaryOpNode.left.getNodeType(), op2type = binaryOpNode.right.getNodeType();
        if(op1type == Type.INTEGER && op2type == Type.INTEGER) return Type.INTEGER;
        if(op1type == Type.INTEGER && op2type == Type.REAL) return Type.REAL;
        if(op1type == Type.REAL && op2type == Type.INTEGER) return Type.REAL;
        if(op1type == Type.REAL && op2type == Type.REAL) return Type.REAL;
        return null;
    }
}
