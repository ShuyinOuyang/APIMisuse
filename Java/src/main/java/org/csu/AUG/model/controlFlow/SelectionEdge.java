package org.csu.AUG.model.controlFlow;

import org.csu.AUG.model.Node;
import org.csu.AUG.visitor.EdgeVisitor;

public class SelectionEdge extends ConditionEdge {
    public SelectionEdge(Node source, Node target) {
        super(source, target, ConditionType.SELECTION);
    }

    @Override
    public <R> R apply(EdgeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
