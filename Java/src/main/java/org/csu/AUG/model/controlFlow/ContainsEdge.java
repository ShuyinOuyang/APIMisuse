package org.csu.AUG.model.controlFlow;

import org.csu.AUG.model.BaseEdge;
import org.csu.AUG.model.Node;
import org.csu.AUG.visitor.EdgeVisitor;

public class ContainsEdge extends BaseEdge {
    public ContainsEdge(Node source, Node target) {
        super(source, target, Type.CONTAINS);
    }

    @Override
    public <R> R apply(EdgeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
