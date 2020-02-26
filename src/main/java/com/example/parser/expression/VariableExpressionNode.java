package com.example.parser.expression;

public class VariableExpressionNode implements ExpressionNode {

    private String name;
    private double value;
    private boolean valueSet;

    public VariableExpressionNode(String name) {
        this.name = name;
    }

    @Override
    public ExpressionNodeType getType() {
        return ExpressionNodeType.VARIABLE_NODE;
    }

    public void setValue(double value) {
        this.value = value;
        this.valueSet = true;
    }

    @Override
    public double getValue() {
        if (valueSet) {
            return value;
        };

        throw new EvaluationException("Variable '" + name + "' was not initialized.");
    }
}
