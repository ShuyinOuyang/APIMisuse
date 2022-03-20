package org.csu.AUG.model.data;

import org.csu.AUG.model.BaseNode;
import org.csu.AUG.model.DataNode;
import org.csu.AUG.visitor.NodeVisitor;

public class AnonymousObjectNode extends BaseNode implements DataNode {
    private final String dataType;

    public AnonymousObjectNode(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getType() {
        return dataType;
    }

    @Override
    public String getName() {
        return "Some(" + dataType + ")";
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
