package com.example.parser.expression;

public class ExponentiationExpressionNode implements ExpressionNode {

    private ExpressionNode base;
    private ExpressionNode exponent;

    public ExponentiationExpressionNode(ExpressionNode base, ExpressionNode exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public ExpressionNodeType getType() {
        return ExpressionNodeType.EXPONENTIATION_NODE;
    }

    @Override
    public double getValue() {
        return Math.pow(base.getValue(), exponent.getValue());
    }
}
