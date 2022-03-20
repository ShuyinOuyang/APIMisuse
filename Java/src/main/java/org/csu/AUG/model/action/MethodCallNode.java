package org.csu.AUG.model.action;

import org.csu.AUG.model.ActionNode;
import org.csu.AUG.model.BaseNode;
import org.csu.AUG.visitor.NodeVisitor;

import java.util.Optional;

public class MethodCallNode extends BaseNode implements ActionNode {
    private final String declaringTypeName;
    private final String methodSignature;

    public MethodCallNode(String declaringTypeName, String methodSignature) {
        this.declaringTypeName = declaringTypeName;
        this.methodSignature = methodSignature;
    }

    public MethodCallNode(String declaringTypeName, String methodSignature, int sourceLineNumber) {
        super(sourceLineNumber);
        this.declaringTypeName = declaringTypeName;
        this.methodSignature = methodSignature;
    }

    @Override
    public boolean isCoreAction() {
        return !getMethodSignature().startsWith("get");
    }

    @Override
    public Optional<String> getAPI() {
        String declaringType = getDeclaringTypeName();
        if (!declaringType.isEmpty() && !declaringType.endsWith("[]"))
            return Optional.of(declaringType);
        else
            return Optional.empty();
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public String getDeclaringTypeName() {
        return declaringTypeName;
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
