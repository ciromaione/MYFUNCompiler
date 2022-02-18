package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class OrOp extends BinaryOpNode {
    public OrOp(ExprNode left, ExprNode right) {
        super("OrOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}