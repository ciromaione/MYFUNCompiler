package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class PowOp extends BinaryOpNode {
    public PowOp(ExprNode left, ExprNode right) {
        super("PowOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
