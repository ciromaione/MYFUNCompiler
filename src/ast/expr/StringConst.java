package ast.expr;

import visitor.Visitor;

public class StringConst extends ConstNode {
    public String value;

    public StringConst(String val) {
        super("string_const");
        this.value = val;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
