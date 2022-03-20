package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class SuperConstructorCallNode extends ConstructorCallNode {
    public SuperConstructorCallNode(String superTypeName) {
        super(superTypeName);
    }

    public SuperConstructorCallNode(String superTypeName, int sourceLineNumber) {
        super(superTypeName, sourceLineNumber);
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}