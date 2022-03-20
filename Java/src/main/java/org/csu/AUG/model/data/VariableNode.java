package org.csu.AUG.model.data;

import org.csu.AUG.model.BaseNode;
import org.csu.AUG.model.DataNode;
import org.csu.AUG.visitor.NodeVisitor;

public class VariableNode extends BaseNode implements DataNode {
    private final String variableType;
    private final String variableName;

    public VariableNode(String variableType, String variableName) {
        this.variableType = variableType;
        this.variableName = variableName;
    }

    @Override
    public String getName() {
        return variableName;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getType() {
        return variableType;
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
