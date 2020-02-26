package com.example.parser;

import com.example.parser.expression.AdditionExpressionNode;
import com.example.parser.expression.ConstantExpressionNode;
import com.example.parser.expression.ExponentiationExpressionNode;
import com.example.parser.expression.ExpressionNode;
import com.example.parser.expression.ExpressionNodeType;
import com.example.parser.expression.FunctionExpressionNode;
import com.example.parser.expression.MinMaxExpressionNode;
import com.example.parser.expression.MultiplicationExpressionNode;
import com.example.parser.expression.VariableExpressionNode;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private LinkedList<Token> tokens;
    private Token lookahead;

    public ExpressionNode parse(List<Token> tokens) {
        this.tokens = new LinkedList<>(tokens);
        lookahead = this.tokens.get(0);

        ExpressionNode expression = expression();

        if(lookahead.token != Token.EPSILON) {
            throw new ParserException(String.format("Unexpected symbol %s found", lookahead.sequence));
        }

        return expression;
    }

    private ExpressionNode expression() {
        // expression -> signed_term sum_op
        ExpressionNode expr = signedTerm();
        return sumOp(expr);
    }

    private ExpressionNode sumOp(ExpressionNode expr) {
        if (lookahead.token == Token.PLUSMINUS) {
            AdditionExpressionNode sum;
            if (expr.getType() == ExpressionNodeType.ADDITION_NODE) {
                sum = (AdditionExpressionNode) expr;
            } else {
                sum = new AdditionExpressionNode(expr, true);   //TODO: WHY?
            }
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = term();
            sum.add(t, positive);

            return sumOp(sum);
        }

        return expr;
    }

    private ExpressionNode term() {
        // term -> factor term_op
        ExpressionNode factor = factor();
        return termOp(factor);
    }

    private ExpressionNode termOp(ExpressionNode expression) {
        if (lookahead.token == Token.MULTDIV) {
            // term_op -> MULDIV factor term_op
            MultiplicationExpressionNode prod;

            if (expression.getType() == ExpressionNodeType.MULTIPLICATION_NODE) {
                prod = (MultiplicationExpressionNode) expression;
            } else {
                prod = new MultiplicationExpressionNode(expression, true);  //TODO: what is this check?
            }

            boolean positive = lookahead.sequence.equals("*");

            nextToken();
            ExpressionNode f = signedFactor();
            prod.add(f, positive);
            return termOp(prod);
        }

        // term_op -> EPSILON
        return expression;
    }

    private ExpressionNode signedFactor() {
        if (lookahead.token == Token.PLUSMINUS) {
            // signed_factor -> PLUSMINUS factor
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode factor = factor();
            if (positive) {
                return factor;
            } else {
                return new AdditionExpressionNode(factor, false);
            }
        }

        // signed_factor -> factor
        return factor();

    }

    private ExpressionNode factor() {
        // factor -> argument factor_op
        ExpressionNode argument = argument();
        return factorOp(argument);
    }

    private ExpressionNode factorOp(ExpressionNode expression) {
        if (lookahead.token == Token.RAISED) {
            // factor_op -> RAISED expression
            nextToken();
            ExpressionNode exponent = signedFactor();
            return new ExponentiationExpressionNode(expression, exponent);
        }
        // factor_op -> EPSILON;
        return expression;
    }

    private ExpressionNode argument() {
        if (lookahead.token == Token.FUNCTION) {
            // argument -> FUNCTION argument
            int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);
            nextToken();
            ExpressionNode expr = argument();
            return new FunctionExpressionNode(function, expr);
        }

        if (lookahead.token == Token.OPEN_BRACKET) {
            // argument -> OPEN_BRACKET sum CLOSE_BRACKET
            nextToken();
            ExpressionNode expression = expression();
            if (lookahead.token != Token.CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected and " + lookahead.sequence + " found instead");
            }

            nextToken();
            return expression;
        }

        if (lookahead.token == Token.MINMAX) {
            // argument -> MINMAX OPEN_BRACKET expression COMMA expression CLOSE_BRACKET
            String operation = lookahead.sequence;
            boolean isMax = lookahead.sequence.equals("max");

            nextToken();
            if (lookahead.token != Token.OPEN_BRACKET) {
                throw new ParserException("Open brackets expected after " + operation + " but receive " + lookahead.sequence);
            }

            nextToken();
            ExpressionNode firstExpression = expression();

            if (lookahead.token != Token.COMMA) {
                throw new ParserException("Commas expected but receive " + lookahead.sequence);
            }

            nextToken();
            ExpressionNode secondExpression = expression();

            if (lookahead.token != Token.CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected and " + lookahead.sequence + " found instead");
            }

            nextToken();
            return new MinMaxExpressionNode(firstExpression, secondExpression, isMax);
        }

        // argument -> value
        return value();
    }

    private ExpressionNode value()
    {
        if (lookahead.token == Token.NUMBER) {
            // argument -> NUMBER
            ExpressionNode expr = new ConstantExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        if (lookahead.token == Token.VARIABLE) {
            // argument -> VARIABLE
            ExpressionNode expr = new VariableExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        if (lookahead.token == Token.EPSILON) {
            throw new ParserException("Unexpected end of input");
        }
        throw new ParserException(String.format("Unexpected symbol %s found", lookahead.sequence));
    }

    private ExpressionNode signedTerm() {
        if (lookahead.token == Token.PLUSMINUS) {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = term();
            if (positive)
                return t;
            else
                return new AdditionExpressionNode(t, false);
        }

        // signed_term -> term
        return term();
    }


    private void nextToken() {
        tokens.poll();

        if (tokens.isEmpty()) {
            lookahead = new Token(Token.EPSILON, "");
        } else {
            lookahead = tokens.getFirst();
        }
    }
}
