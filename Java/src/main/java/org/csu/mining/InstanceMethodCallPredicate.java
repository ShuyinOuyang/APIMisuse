package org.csu.mining;


import org.csu.AUG.model.Node;
import org.csu.AUG.model.action.ConstructorCallNode;
import org.csu.AUG.model.action.MethodCallNode;

import java.util.function.Predicate;

public class InstanceMethodCallPredicate implements Predicate<Node> {
    @Override
    public boolean test(Node node) {
        return node instanceof MethodCallNode && !(node instanceof ConstructorCallNode);
    }
}
