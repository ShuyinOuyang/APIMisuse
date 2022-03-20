package org.csu.AUG.model.action;

import org.csu.AUG.model.ActionNode;
import org.csu.AUG.model.BaseNode;
import org.csu.AUG.visitor.NodeVisitor;

public class BreakNode extends BaseNode implements ActionNode {
    public BreakNode() {}

    public BreakNode(int sourceLineNumber) {
        super(sourceLineNumber);
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
