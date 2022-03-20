package org.csu.AUG.model.controlFlow;

import org.csu.AUG.model.BaseEdge;
import org.csu.AUG.model.ControlFlowEdge;
import org.csu.AUG.model.Node;
import org.csu.AUG.visitor.EdgeVisitor;

public class OrderEdge extends BaseEdge implements ControlFlowEdge {
    public OrderEdge(Node source, Node target) {
        super(source, target, Type.ORDER);
    }

    @Override
    public <R> R apply(EdgeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
