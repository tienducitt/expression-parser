package com.example.parser.expression;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ExpressionNodeTest {

    @Test
    public void test() {
        AdditionExpressionNode innerSum = new AdditionExpressionNode(); // 1 + 3
        innerSum.add(new ConstantExpressionNode(1), true);
        innerSum.add(new ConstantExpressionNode(3), true);

        ExpressionNode sqrt = new FunctionExpressionNode(FunctionExpressionNode.SQRT, innerSum);    // sqrt(1+3)

        ExpressionNode expo = new ExponentiationExpressionNode(
                new ConstantExpressionNode(2),
                new ConstantExpressionNode(4)
        );  // 2^4

        MultiplicationExpressionNode prod = new MultiplicationExpressionNode();     // 3 * 2^4
        prod.add(new ConstantExpressionNode(3), true);
        prod.add(expo, true);

        AdditionExpressionNode expression = new AdditionExpressionNode();   // 3 * 2^4 + sqrt(1+3)
        expression.add(prod, true);
        expression.add(sqrt, true);

        double value = expression.getValue();
        Assertions.assertThat(value).isEqualTo(50);
    }
}