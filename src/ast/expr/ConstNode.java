package ast.expr;

import ast.ExprNode;
import ast.Node;

public abstract class ConstNode extends ExprNode {
    public ConstNode(String name) {
        super(name);
    }
}
