package ast.stms;

import ast.ExprNode;
import ast.Node;
import ast.ParDeclMode;
import visitor.Visitor;

public class ParamOp extends Node {
    public ParDeclMode mode;
    public ExprNode expr;

    public ParamOp(ParDeclMode mode, ExprNode expr) {
        super("ParamOp");
        this.mode = mode;
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
