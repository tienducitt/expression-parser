package com.example.parser;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class TokenizerTest {

    @Test
    public void testForMinMax() {
        String token = "min(1,2)";
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenize(token);
        List<Token> tokens = tokenizer.getTokens();

        System.out.println(tokens);
    }
}