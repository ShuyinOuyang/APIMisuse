package org.csu.AUG.model;

import org.csu.AUG.visitor.NodeVisitor;

import java.io.Serializable;
import java.util.Optional;

public interface Node extends Cloneable, Serializable {
    int getId();

    void setGraph(APIUsageGraph aug);


    @Deprecated
    APIUsageGraph getGraph();

    default boolean isCoreAction() {
        return false;
    }

    default Optional<String> getAPI() {
        return Optional.empty();
    }

    Node clone();

    <R> R apply(NodeVisitor<R> visitor);
}
