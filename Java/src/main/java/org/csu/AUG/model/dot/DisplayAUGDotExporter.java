package org.csu.AUG.model.dot;

import org.csu.AUG.visitor.BaseAUGLabelProvider;
import org.csu.AUG.visitor.WithSourceLineNumberLabelProvider;

public class DisplayAUGDotExporter extends AUGDotExporter {
    public DisplayAUGDotExporter() {
        super(new WithSourceLineNumberLabelProvider(new BaseAUGLabelProvider()),
                new AUGNodeAttributeProvider(),
                new AUGEdgeAttributeProvider());
    }
}
