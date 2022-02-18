package ast.expr;

import ast.ExprNode;
import ast.Node;

public abstract class BinaryOpNode extends ExprNode {
    public ExprNode left;
    public ExprNode right;

    public BinaryOpNode(String name) {
        super(name);
    }
}
