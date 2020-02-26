package com.example.parser;

public class Token {
    public static final int EPSILON = 0;
    public static final int PLUSMINUS = 1;
    public static final int MULTDIV = 2;
    public static final int RAISED = 3;
    public static final int FUNCTION = 4;
    public static final int OPEN_BRACKET = 5;
    public static final int CLOSE_BRACKET = 6;
    public static final int NUMBER = 7;
    public static final int VARIABLE = 8;
    public static final int MINMAX = 9;
    public static final int COMMA = 10;


    public final int token;
    public final String sequence;

    public Token(int token, String sequence) {
        this.token = token;
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "Token{" +
            "token=" + token +
            ", sequence='" + sequence + '\'' +
            '}';
    }
}
