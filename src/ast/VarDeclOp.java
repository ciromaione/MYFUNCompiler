package ast;

import visitor.Visitor;

import java.util.List;

public class VarDeclOp extends Node {
    public Type type;
    public List<IdInitOp> idInitOps;

    public VarDeclOp(Type type, List<IdInitOp> idInitOps) {
        super("VarDeclOp");
        this.type = type;
        this.idInitOps = idInitOps;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
