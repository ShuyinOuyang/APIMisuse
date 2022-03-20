package org.csu.AUG.model.patterns;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.csu.AUG.model.BaseNode;
import org.csu.AUG.model.DataNode;
import org.csu.AUG.visitor.NodeVisitor;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AggregateDataNode extends BaseNode implements DataNode {
    private final String dataType;
    private Set<DataNode> aggregatedNodes;

    public AggregateDataNode(String dataType, Set<DataNode> aggregatedNodes) {
        this.dataType = dataType;
        this.aggregatedNodes = aggregatedNodes;
    }

    @Override
    public String getName() {
        return getFirstOrNull(getAggregatedNames());
    }

    public Multiset<String> getAggregatedNames() {
        return mapAggregatedNodes(DataNode::getName);
    }

    @Override
    public String getValue() {
        return getFirstOrNull(getAggregatedValues());
    }

    public Multiset<String> getAggregatedValues() {
        return mapAggregatedNodes(DataNode::getValue);
    }

    @Override
    public String getType() {
        return dataType;
    }

    private <R> HashMultiset<R> mapAggregatedNodes(Function<DataNode, R> getName) {
        return HashMultiset.create(aggregatedNodes.stream()
                .map(getName).filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    private <R> R getFirstOrNull(Multiset<R> aggregatedNames) {
        if (aggregatedNames.isEmpty()) {
            return null;
        } else {
            return aggregatedNames.iterator().next();
        }
    }

    @Override
    public <R> R apply(NodeVisitor<R> visitor) {
        return getFirstOrNull(mapAggregatedNodes(node -> node.apply(visitor)));
    }
}
