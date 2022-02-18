package ast.expr;

import visitor.Visitor;

public class RealConst extends ConstNode {
    public float value;

    public RealConst(float val) {
        super("real_const");
        this.value = val;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
