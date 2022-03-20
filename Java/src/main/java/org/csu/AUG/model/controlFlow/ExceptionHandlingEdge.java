package org.csu.AUG.model.controlFlow;

import org.csu.AUG.model.BaseEdge;
import org.csu.AUG.model.DataFlowEdge;
import org.csu.AUG.model.Node;
import org.csu.AUG.visitor.EdgeVisitor;

public class ExceptionHandlingEdge extends BaseEdge implements DataFlowEdge {
    public ExceptionHandlingEdge(Node source, Node target) {
        super(source, target, Type.EXCEPTION_HANDLING);
    }

    @Override
    public <R> R apply(EdgeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
