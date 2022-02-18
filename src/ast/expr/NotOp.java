package ast.expr;

import ast.ExprNode;
import ast.Node;
import visitor.Visitor;

public class NotOp extends ExprNode {
    public ExprNode right;

    public NotOp(ExprNode right) {
        super("NotOp");
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
