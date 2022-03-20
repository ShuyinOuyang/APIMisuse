package org.csu.AUG.model.dot;

import org.csu.AUG.model.APIUsageGraph;

import java.util.Map;

public interface AUGAttributeProvider<G extends APIUsageGraph> {
    Map<String, String> getAUGAttributes(G aug);
}
