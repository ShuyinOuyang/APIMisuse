package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class ArrayCreationNode extends ConstructorCallNode {
    public ArrayCreationNode(String baseType) {
        super(baseType);
    }

    public ArrayCreationNode(String baseType, int sourceLineNumber) {
        super(baseType, sourceLineNumber);
    }

    @Override
    public boolean isCoreAction() {
        return false;
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
