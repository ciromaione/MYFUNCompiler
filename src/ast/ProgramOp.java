package ast;

import visitor.Visitor;

import java.util.List;

public class ProgramOp extends Node {
    public List<VarDeclOp> varDeclOps;
    public List<FunOp> funOps;
    public BodyOp bodyOp;

    public ProgramOp(List<VarDeclOp> varDeclOps, List<FunOp> funOps, BodyOp bodyOp) {
        super("ProgramOp");
        this.varDeclOps = varDeclOps;
        this.funOps = funOps;
        this.bodyOp = bodyOp;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
