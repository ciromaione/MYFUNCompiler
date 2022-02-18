package ast.expr;

import visitor.Visitor;

public class BoolConst extends ConstNode {
    public boolean value;

    public BoolConst(boolean val) {
        super("bool_const");
        this.value = val;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
