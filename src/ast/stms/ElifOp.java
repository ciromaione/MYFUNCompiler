package ast.stms;

import ast.BodyOp;
import ast.ExprNode;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

public class ElifOp extends Node implements StatNode {
    public ExprNode cond;
    public BodyOp body;

    public ElifOp(ExprNode cond, BodyOp body) {
        super("ElifOp");
        this.cond = cond;
        this.body = body;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
