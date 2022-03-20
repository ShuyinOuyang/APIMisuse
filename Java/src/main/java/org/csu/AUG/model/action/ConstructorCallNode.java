package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class ConstructorCallNode extends MethodCallNode {
    public ConstructorCallNode(String typeName) {
        super(typeName, "<init>");
    }

    public ConstructorCallNode(String typeName, int sourceLineNumber) {
        super(typeName, "<init>", sourceLineNumber);
    }

    @Override
    public boolean isCoreAction() {
        return true;
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
