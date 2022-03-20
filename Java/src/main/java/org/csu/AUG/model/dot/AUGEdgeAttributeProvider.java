package org.csu.AUG.model.dot;

import org.csu.AUG.model.DataFlowEdge;
import org.csu.AUG.model.Edge;
import org.jgrapht.ext.ComponentAttributeProvider;


import java.util.LinkedHashMap;
import java.util.Map;

public class AUGEdgeAttributeProvider implements ComponentAttributeProvider<Edge> {
    @Override
    public Map<String, String> getComponentAttributes(Edge edge) {
        final LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        String style;
        if (edge instanceof DataFlowEdge) {
            style = edge.isDirect() ? "solid" : "dotted";
        } else {
            style = edge.isDirect() ? "bold" : "dashed";
        }
        attributes.put("style", style);
        return attributes;
    }
}
