package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class StrCatOp extends BinaryOpNode {
    public StrCatOp(ExprNode left, ExprNode right) {
        super("StrCatOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
