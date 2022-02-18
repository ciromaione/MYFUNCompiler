package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class GEOp extends BinaryOpNode {
    public GEOp(ExprNode left, ExprNode right) {
        super("GEOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}