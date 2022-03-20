package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class ArrayAssignmentNode extends MethodCallNode {
    public ArrayAssignmentNode(String arrayTypeName) {
        super(arrayTypeName, "arrayset()");
    }

    public ArrayAssignmentNode(String arrayTypeName, int sourceLineNumber) {
        super(arrayTypeName, "arrayset()", sourceLineNumber);
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
