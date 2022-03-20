package org.csu.AUG.model.controlFlow;

import org.csu.AUG.model.Node;
import org.csu.AUG.visitor.EdgeVisitor;

public class RepetitionEdge extends ConditionEdge {
    public RepetitionEdge(Node source, Node target) {
        super(source, target, ConditionType.REPETITION);
    }

    @Override
    public <R> R apply(EdgeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
