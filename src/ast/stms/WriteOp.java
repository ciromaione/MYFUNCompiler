package ast.stms;

import ast.ExprNode;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

public class WriteOp extends Node implements StatNode {
    public enum WriteType {
        WRITE("write"),
        WRITELN("writeln"),
        WRITET("writet"),
        WRITEB("writeb");

        private String name;

        WriteType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public WriteType writeType;
    public ExprNode expr;

    public WriteOp(WriteType writeType, ExprNode expr) {
        super("WriteOp");
        this.writeType = writeType;
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
