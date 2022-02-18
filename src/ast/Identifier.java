package ast;

import visitor.Visitor;

public class Identifier extends ExprNode {
    public String idName;

    public Identifier(String idName) {
        super("id");
        this.idName = idName;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "(" + this.name + ", \"" + this.idName + "\")";
    }
}
