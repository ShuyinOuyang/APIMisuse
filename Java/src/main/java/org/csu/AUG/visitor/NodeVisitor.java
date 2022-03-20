package org.csu.AUG.visitor;

import org.csu.AUG.model.data.*;
import org.csu.AUG.model.action.*;

public interface NodeVisitor<R> {

    // Data Nodes
    R visit(AnonymousClassMethodNode node);
    R visit(AnonymousObjectNode node);
    R visit(ConstantNode node);
    R visit(ExceptionNode node);
    R visit(LiteralNode node);
    R visit(VariableNode node);
    // Actions
    R visit(ArrayAccessNode node);
    R visit(ArrayAssignmentNode node);
    R visit(ArrayCreationNode node);
    R visit(AssignmentNode node);
    R visit(BreakNode node);
    R visit(CastNode node);
    R visit(CatchNode node);
    R visit(ConstructorCallNode node);
    R visit(ContinueNode node);
    R visit(InfixOperatorNode node);
    R visit(MethodCallNode node);
    R visit(NullCheckNode node);
    R visit(ReturnNode node);
    R visit(SuperConstructorCallNode node);
    R visit(ThrowNode node);
    R visit(TypeCheckNode node);
    R visit(UnaryOperatorNode node);
}