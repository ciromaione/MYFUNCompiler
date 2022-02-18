package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class LTOp extends BinaryOpNode {
    public LTOp(ExprNode left, ExprNode right) {
        super("LTOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}