package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class UnaryOperatorNode extends OperatorNode {
    public UnaryOperatorNode(String operator) {
        super(operator);
    }

    public UnaryOperatorNode(String operator, int sourceLineNumber) {
        super(operator, sourceLineNumber);
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}