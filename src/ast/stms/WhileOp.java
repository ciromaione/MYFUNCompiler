package ast.stms;

import ast.BodyOp;
import ast.ExprNode;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

public class WhileOp extends Node implements StatNode {
    public ExprNode condition;
    public BodyOp bodyOp;

    public WhileOp(ExprNode condition, BodyOp bodyOp) {
        super("WhileOp");
        this.condition = condition;
        this.bodyOp = bodyOp;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
