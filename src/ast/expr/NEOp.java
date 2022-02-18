package ast.expr;

import ast.ExprNode;
import visitor.Visitor;

public class NEOp extends BinaryOpNode {
    public NEOp(ExprNode left, ExprNode right) {
        super("NEOp");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}