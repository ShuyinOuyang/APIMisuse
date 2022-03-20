package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class InfixOperatorNode extends OperatorNode {
    public InfixOperatorNode(String operator) {
        super(operator);
    }

    public InfixOperatorNode(String operator, int sourceLineNumber) {
        super(operator, sourceLineNumber);
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
