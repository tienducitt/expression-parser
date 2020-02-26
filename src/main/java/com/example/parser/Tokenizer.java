package com.example.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private List<TokenInfo> tokenInfos;
    private List<Token> tokens;

    public Tokenizer() {
        this.tokenInfos = new LinkedList<>();
        this.tokens = new LinkedList<>();
        this.add("sin|cos|exp|ln|sqrt", 4); // function
        this.add("min|max", 9); // min, max
        this.add("[,]", 10); // comma
        this.add("\\(", 5); // open bracket
        this.add("\\)", 6); // close bracket
        this.add("[+-]", 1); // plus or minus
        this.add("[*/]", 2); // mult or divide
        this.add("\\^", 3); // raised
        this.add("[0-9]+([.][0-9]+)?",7); // integer number
        this.add("[a-zA-Z][a-zA-Z0-9_]*", 8); // variable
    }

    private void add(String regex, int token) {
        tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), token));
    }

    public void tokenize(String str) {
        String s = str.trim();
        tokens.clear();

        while (!"".equals(s)) {
            boolean match = false;
            for (TokenInfo tokenInfo : tokenInfos) {
                Matcher matcher = tokenInfo.regex.matcher(s);
                if (matcher.find()) {
                    match = true;

                    String tok = matcher.group().trim();
                    tokens.add(new Token(tokenInfo.token, tok));
                    s = matcher.replaceFirst("").trim();
                    break;
                }
            }

            if (!match) throw new ParserException("Unexpected character in input: \"" + s + "\", at position: " + (str.length() - s.length()));
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private class TokenInfo {
        public final Pattern regex;
        public final int token;

        public TokenInfo(Pattern regex, int token) {
            this.regex = regex;
            this.token = token;
        }
    }
}
