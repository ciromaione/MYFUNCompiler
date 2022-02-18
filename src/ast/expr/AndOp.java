package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class AndOp extends BinaryOpNode {
    public AndOp(ExprNode left, ExprNode right) {
        super("AndOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}