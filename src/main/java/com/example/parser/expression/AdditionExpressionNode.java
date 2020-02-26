package com.example.parser.expression;

public class AdditionExpressionNode extends SequenceExpressionNode {

    public AdditionExpressionNode() {
    }

    public AdditionExpressionNode(ExpressionNode node, boolean positive) {
        super(node, positive);
    }

    @Override
    public ExpressionNodeType getType() {
        return ExpressionNodeType.ADDITION_NODE;
    }

    @Override
    public double getValue() {
        double sum = 0.0;
        for (Term t : terms) {
            if (t.positive) {
                sum += t.expression.getValue();
            } else {
                sum -= t.expression.getValue();
            }
        }

        return sum;
    }
}
