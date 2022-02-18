package ast.stms;

import ast.ExprNode;
import ast.Identifier;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

public class AssignOp extends Node implements StatNode {
    public Identifier id;
    public ExprNode expr;

    public AssignOp(Identifier id, ExprNode expr) {
        super("AssignOp");
        this.id = id;
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
