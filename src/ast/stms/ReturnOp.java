package ast.stms;

import ast.ExprNode;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

public class ReturnOp extends Node implements StatNode {
    public ExprNode expr;

    public ReturnOp(ExprNode expr) {
        super("ReturnOp");
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
