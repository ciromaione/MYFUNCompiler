package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class AddOp extends BinaryOpNode {
    public AddOp(ExprNode left, ExprNode right) {
        super("AddOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
