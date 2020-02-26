package com.example.parser.expression;

public class MultiplicationExpressionNode extends SequenceExpressionNode {

    public MultiplicationExpressionNode() {
        super();
    }

    public MultiplicationExpressionNode(ExpressionNode node, boolean positive) {
        super(node, positive);
    }

    @Override
    public ExpressionNodeType getType() {
        return ExpressionNodeType.MULTIPLICATION_NODE;
    }

    @Override
    public double getValue() {
        double ret = 1.0;
        for (Term term : terms) {
            if (term.positive) {
                ret *= term.expression.getValue();
            } else {
                ret /= term.expression.getValue();
            }
        }

        return ret;
    }
}
