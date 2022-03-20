package org.csu.AUG.model.controlFlow;

import org.csu.AUG.model.BaseEdge;
import org.csu.AUG.model.ControlFlowEdge;
import org.csu.AUG.model.Node;

public abstract class ConditionEdge extends BaseEdge implements ControlFlowEdge {
    private final ConditionType conditionType;

    public enum ConditionType {
        SELECTION("sel"), REPETITION("rep");

        private final String label;

        ConditionType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    protected ConditionEdge(Node source, Node target, ConditionType conditionType) {
        super(source, target, Type.CONDITION);
        this.conditionType = conditionType;
    }

    /**
     * Use the edge's class type instead.
     */
    @Deprecated
    public ConditionType getConditionType() {
        return conditionType;
    }
}