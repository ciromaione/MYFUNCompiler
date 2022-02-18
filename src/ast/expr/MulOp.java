package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class MulOp extends BinaryOpNode {
    public MulOp(ExprNode left, ExprNode right) {
        super("MulOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}