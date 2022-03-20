package org.csu.AUG.model;

public abstract class BaseEdge implements Edge{
    private Node source;
    private Node target;
    private final Type type;
    private APIUsageGraph graph;

    protected BaseEdge(Node source, Node target, Type type) {
        this.source = source;
        this.target = target;
        this.type = type;
    }
    @Deprecated
    @Override
    public Node getSource() {
        return source;
    }
    @Deprecated
    @Override
    public Node getTarget() {
        return target;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Edge clone() {
        try {
            return (Edge) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("All edges must be cloneable.", e);
        }
    }

    @Override
    public Edge clone(Node newSourceNode, Node newTargetNode) {
        BaseEdge clone = (BaseEdge) clone();
        clone.source = newSourceNode;
        clone.target = newTargetNode;
        return clone;
    }

    @Override
    public String toString() {
//        return getSource() + "-(" + new BaseAUGLabelProvider().getLabel(this) + ")->" + getTarget();
        return getSource() + "-(" + ")->" + getTarget();
}
}
