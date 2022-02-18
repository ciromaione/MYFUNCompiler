package ast;

import visitor.Visitor;

public abstract class Node {
    public String functionIdName;
    public String name;
    private Type nodeType = null;

    public Node(String name) {
        this.name = name;
        this.functionIdName = "";
    }

    abstract public Object accept(Visitor v);

    public Type getNodeType() {
        return nodeType;
    }

    public void setNodeType(Type nodeType) {
        if(nodeType == Type.VAR)
            throw new RuntimeException("The node type cannot be 'VAR'");
        this.nodeType = nodeType;
    }
}
