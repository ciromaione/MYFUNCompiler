package ast.expr;

import ast.ExprNode;
import ast.Node;
import visitor.Visitor;

public class UminusOp extends ExprNode {
    public ExprNode right;

    public UminusOp(ExprNode right) {
        super("UminusOp");
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
