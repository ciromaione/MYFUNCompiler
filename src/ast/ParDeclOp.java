package ast;

import visitor.Visitor;

public class ParDeclOp extends Node {
    public ParDeclMode mode;
    public Type type;
    public Identifier id;

    public ParDeclOp(ParDeclMode mode, Type type, Identifier id) {
        super("ParDeclOp");
        this.mode = mode;
        this.type = type;
        this.id = id;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
