package ast;

import visitor.Visitor;

public class IdInitOp extends Node {
    private Type idType;
    public Identifier id;
    public ExprNode expr;

    public IdInitOp(Identifier id, ExprNode expr) {
        super("IdInitOp");
        this.id = id;
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public Type getIdType() {
        return idType;
    }

    public void setIdType(Type idType) {
        if(idType == null || idType == Type.VAR)
            throw new RuntimeException("The real type of a variable must not be VAR!");
        this.idType = idType;
    }
}
