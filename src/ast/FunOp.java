package ast;

import visitor.Visitor;

import java.util.List;

public class FunOp extends Node {
    public Identifier id;
    public List<ParDeclOp> parDeclOps;
    public Type type;
    public BodyOp bodyOp;

    public FunOp(Identifier id, List<ParDeclOp> parDeclOps, Type type, BodyOp bodyOp) {
        super("FunOp");
        this.id = id;
        this.parDeclOps = parDeclOps;
        this.type = type;
        this.bodyOp = bodyOp;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
