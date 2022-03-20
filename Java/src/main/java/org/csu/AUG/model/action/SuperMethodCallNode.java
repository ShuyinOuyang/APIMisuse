package org.csu.AUG.model.action;

import org.csu.AUG.visitor.NodeVisitor;

public class SuperMethodCallNode extends MethodCallNode {
    public SuperMethodCallNode(String declaringTypeName, String methodSignature) {
        super(declaringTypeName, methodSignature);
    }

    public SuperMethodCallNode(String declaringTypeName, String methodSignature, int sourceLineNumber) {
        super(declaringTypeName, methodSignature, sourceLineNumber);
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}