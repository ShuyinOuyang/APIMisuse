package org.csu.AUG.visitor;

import org.csu.AUG.model.Edge;
import org.csu.AUG.model.Node;

public interface AUGLabelProvider extends AUGElementVisitor<String> {
    default String getLabel(Node node) {
        return node.apply(this);
    }

    default String getLabel(Edge edge) {
        return edge.apply(this);
    }
}
