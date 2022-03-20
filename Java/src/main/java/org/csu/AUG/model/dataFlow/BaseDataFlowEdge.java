package org.csu.AUG.model.dataFlow;

import org.csu.AUG.model.BaseEdge;
import org.csu.AUG.model.DataFlowEdge;
import org.csu.AUG.model.Node;

public abstract class BaseDataFlowEdge extends BaseEdge implements DataFlowEdge {
    public BaseDataFlowEdge(Node source, Node target, Type type) {
        super(source, target, type);
    }
}
