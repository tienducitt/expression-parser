package com.example.parser.expression;

import java.util.ArrayList;
import java.util.List;

public abstract  class SequenceExpressionNode implements ExpressionNode {
    protected List<Term> terms;     //TODO: this should only has 2 terms

    public SequenceExpressionNode() {
        terms = new ArrayList<>();
    }

    public SequenceExpressionNode(List<Term> terms) {
        this.terms = terms;
    }

    public SequenceExpressionNode(ExpressionNode node, boolean positive) {
        this.terms = new ArrayList<>();
        this.terms.add(new Term(positive, node));
    }

    public void add(ExpressionNode node, boolean positive) {
        this.terms.add(new Term(positive, node));
    }
}
