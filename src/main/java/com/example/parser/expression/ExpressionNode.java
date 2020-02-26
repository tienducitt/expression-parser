package com.example.parser.expression;

public interface ExpressionNode {
    ExpressionNodeType getType();
    double getValue();
}
