package org.csu.AUG.visitor;

public interface AUGElementVisitor<R> extends NodeVisitor<R>, EdgeVisitor<R> {
}