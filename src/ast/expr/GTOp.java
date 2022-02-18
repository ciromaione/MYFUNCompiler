package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class GTOp extends BinaryOpNode {
    public GTOp(ExprNode left, ExprNode right) {
        super("GTOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}