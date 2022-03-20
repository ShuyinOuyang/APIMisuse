package org.csu.AUG.model.action;

import org.csu.AUG.model.ActionNode;
import org.csu.AUG.model.BaseNode;
import org.csu.AUG.visitor.NodeVisitor;

public class CatchNode extends BaseNode implements ActionNode {
    private final String exceptionType;

    public CatchNode(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public CatchNode(String exceptionType, int sourceLineNumber) {
        super(sourceLineNumber);
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
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
