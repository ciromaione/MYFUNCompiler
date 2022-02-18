package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class EQOp extends BinaryOpNode {
    public EQOp(ExprNode left, ExprNode right) {
        super("EQOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}