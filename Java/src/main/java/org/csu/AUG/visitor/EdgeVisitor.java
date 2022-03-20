package org.csu.AUG.visitor;

import org.csu.AUG.model.controlFlow.*;
import org.csu.AUG.model.dataFlow.*;

public interface EdgeVisitor<R> {
    // Control Flow
    R visit(ContainsEdge edge);
    R visit(ExceptionHandlingEdge edge);
    R visit(FinallyEdge edge);
    R visit(OrderEdge edge);
    R visit(RepetitionEdge edge);
    R visit(SelectionEdge edge);
    R visit(SynchronizationEdge edge);
    R visit(ThrowEdge edge);
    // Data Flow
    R visit(DefinitionEdge edge);
    R visit(ParameterEdge edge);
    R visit(QualifierEdge edge);
    R visit(ReceiverEdge edge);
}
