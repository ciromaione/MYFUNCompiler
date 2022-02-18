package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class DivOp extends BinaryOpNode {
    public DivOp(ExprNode left, ExprNode right) {
        super("DivOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
