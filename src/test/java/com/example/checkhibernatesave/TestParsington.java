package com.example.checkhibernatesave;

import com.example.parser.Parser;
import com.example.parser.Token;
import com.example.parser.Tokenizer;
import com.example.parser.expression.ExpressionNode;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TestParsington {

    @Test
    public void test() {
        String token = "3 * 2^(8/2) + sqrt(5-1)";
        Tokenizer tokenizer = newTokenizer();
        tokenizer.tokenize(token);

        Parser parser = new Parser();
        ExpressionNode expression = parser.parse(tokenizer.getTokens());
        double value = expression.getValue();
        Assertions.assertThat(value).isEqualTo(50);

    }

    private Tokenizer newTokenizer() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.add("sin|cos|exp|ln|sqrt", 4); // function
        tokenizer.add("\\(", 5); // open bracket
        tokenizer.add("\\)", 6); // close bracket
        tokenizer.add("[+-]", 1); // plus or minus
        tokenizer.add("[*/]", 2); // mult or divide
        tokenizer.add("\\^", 3); // raised
        tokenizer.add("[0-9]+([,.][0-9]+)?",7); // integer number
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 8); // variable

        return tokenizer;
    }
}
