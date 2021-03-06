package org.csu.AUG.model.data;

import org.csu.AUG.model.BaseNode;
import org.csu.AUG.model.DataNode;
import org.csu.AUG.visitor.NodeVisitor;

public class AnonymousClassMethodNode extends BaseNode implements DataNode {
    private final String baseType;
    private final String methodSignature;

    public AnonymousClassMethodNode(String baseType, String methodSignature) {
        this.baseType = baseType;
        this.methodSignature = methodSignature;
    }

    @Override
    public String getType() {
        return baseType;
    }

    @Override
    public String getName() {
        return methodSignature;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
