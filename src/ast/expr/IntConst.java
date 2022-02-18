package ast.expr;

import visitor.Visitor;

public class IntConst extends ConstNode {
    public int value;

    public IntConst(int val) {
        super("integer_const");
        this.value = val;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
