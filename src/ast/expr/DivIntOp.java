package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class DivIntOp extends BinaryOpNode {
    public DivIntOp(ExprNode left, ExprNode right) {
        super("DivIntOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
