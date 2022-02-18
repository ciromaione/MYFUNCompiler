package ast.stms;

import ast.BodyOp;
import ast.ExprNode;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

import java.util.List;

public class IfStatOp extends Node implements StatNode {
    public ExprNode condition;
    public BodyOp ifBodyOp;
    public BodyOp elseBodyOp;
    public List<ElifOp> elifOpList;

    public IfStatOp(ExprNode condition, BodyOp ifBodyOp, List<ElifOp> elifOpList, BodyOp elseBodyOp) {
        super("IfStatOp");
        this.condition = condition;
        this.ifBodyOp = ifBodyOp;
        this.elseBodyOp = elseBodyOp;
        this.elifOpList = elifOpList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
