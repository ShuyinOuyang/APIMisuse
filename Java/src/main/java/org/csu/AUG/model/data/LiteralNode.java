package org.csu.AUG.model.data;

import org.csu.AUG.model.BaseNode;
import org.csu.AUG.model.DataNode;
import org.csu.AUG.visitor.NodeVisitor;

public class LiteralNode extends BaseNode implements DataNode {
    private final String dataType;
    private final String dataValue;

    public LiteralNode(String dataType, String dataValue) {
        this.dataType = dataType;
        this.dataValue = dataValue;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getValue() {
        return dataValue;
    }

    @Override
    public String getType() {
        return dataType;
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
