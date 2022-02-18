package ast;

import visitor.Visitor;

public interface StatNode {
    Object accept(Visitor v);
}
