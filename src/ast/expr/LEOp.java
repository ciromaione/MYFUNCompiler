package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class LEOp extends BinaryOpNode {
    public LEOp(ExprNode left, ExprNode right) {
        super("LEOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}