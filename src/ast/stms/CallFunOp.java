package ast.stms;

import ast.ExprNode;
import ast.Identifier;
import ast.Node;
import ast.StatNode;
import visitor.Visitor;

import java.util.List;

public class CallFunOp extends ExprNode implements StatNode {
    public Identifier id;
    public List<ParamOp> paramOps;

    public CallFunOp(Identifier id, List<ParamOp> paramOps) {
        super("CallFunOp");
        this.id = id;
        this.paramOps = paramOps;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
