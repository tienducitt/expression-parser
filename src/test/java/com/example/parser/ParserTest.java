package com.example.parser;

import static org.junit.Assert.*;

import com.example.parser.expression.ExpressionNode;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ParserTest {

    @Test
    public void test1() {
        test("3 * 2^(8/2) + sqrt(5-1)", 50);
    }

    @Test
    public void testMin() {
        test("3 * 2^(8/2) + sqrt(5-1) + min(1, 2)", 51);
    }

    @Test
    public void testMax() {
        test("3 * 2^(8/2) + sqrt(5-1) + sqrt(max(1, 4))", 52);
    }

    @Test
    public void testComplexMinMax() {
        test("min(max(1+2,2)*2, min(7, 2)) * 2", 4);
    }

    public void test(String token, double expectedResult) {
        Parser parser = new Parser();
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize(token);
        double value = parser.parse(tokenizer.getTokens()).getValue();
        Assertions.assertThat(value).isEqualTo(expectedResult);
    }
}