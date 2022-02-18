package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class DiffOp extends BinaryOpNode {
    public DiffOp(ExprNode left, ExprNode right) {
        super("DiffOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
