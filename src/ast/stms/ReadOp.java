package ast.stms;

import ast.ExprNode;
import ast.Identifier;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

import java.util.List;

public class ReadOp extends Node implements StatNode {
    public List<Identifier> ids;
    public ExprNode expr;

    public ReadOp(List<Identifier> ids, ExprNode expr) {
        super("ReadOp");
        this.ids = ids;
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
