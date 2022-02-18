package ast;

import visitor.Visitor;

import java.util.List;

public class BodyOp extends Node {
    public String functionIdName;
    public List<VarDeclOp> varDeclOps;
    public List<StatNode> statOps;

    public BodyOp(List<VarDeclOp> varDeclOps, List<StatNode> statOps) {
        super("BodyOp");
        this.varDeclOps = varDeclOps;
        this.statOps = statOps;
        this.functionIdName = null;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
