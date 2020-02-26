package com.example.parser.expression;

public class ConstantExpressionNode implements ExpressionNode {

    private double value;

    public ConstantExpressionNode(double value) {
        this.value = value;
    }

    public ConstantExpressionNode(String value) {
        this.value = Double.valueOf(value);
    }

    @Override
    public ExpressionNodeType getType() {
        return ExpressionNodeType.CONSTANT_NODE;
    }

    @Override
    public double getValue() {
        return value;
    }
}
