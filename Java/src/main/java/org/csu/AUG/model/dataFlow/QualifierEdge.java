package org.csu.AUG.model.dataFlow;

import org.csu.AUG.model.BaseEdge;
import org.csu.AUG.model.DataFlowEdge;
import org.csu.AUG.model.Node;
import org.csu.AUG.visitor.EdgeVisitor;

public class QualifierEdge extends BaseEdge implements DataFlowEdge {
    public QualifierEdge(Node source, Node target) {
        super(source, target, Type.QUALIFIER);
    }

    @Override
    public <R> R apply(EdgeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}