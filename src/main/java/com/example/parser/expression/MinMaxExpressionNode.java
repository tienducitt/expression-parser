package com.example.parser.expression;

public class MinMaxExpressionNode implements ExpressionNode {

    private ExpressionNode firstExpression;
    private ExpressionNode secondExpression;
    private boolean isMax;

    public MinMaxExpressionNode(ExpressionNode firstExpression, ExpressionNode secondExpression, boolean isMax) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.isMax = isMax;
    }

    @Override
    public ExpressionNodeType getType() {
        return ExpressionNodeType.MINMAX;
    }

    @Override
    public double getValue() {
        if (isMax) {
            return Math.max(firstExpression.getValue(), secondExpression.getValue());
        }

        return Math.min(firstExpression.getValue(), secondExpression.getValue());
    }
}
