package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class NullCheckNode extends InfixOperatorNode {
    public NullCheckNode() {
        super("<nullcheck>");
    }

    public NullCheckNode(int sourceLineNumber) {
        super("<nullcheck>", sourceLineNumber);
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
