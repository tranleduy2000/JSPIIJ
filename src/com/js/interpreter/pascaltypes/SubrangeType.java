package com.js.interpreter.pascaltypes;

import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.ExpectedTokenException;
import com.js.interpreter.exceptions.NonConstantExpressionException;
import com.js.interpreter.exceptions.NonIntegerIndexException;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.tokens.Token;
import com.js.interpreter.tokens.basic.DotDotToken;
import com.js.interpreter.tokens.grouping.GrouperToken;

public class SubrangeType {
    public SubrangeType() {
        this.lower = 0;
        this.size = 0;
    }

    public SubrangeType(GrouperToken i, ExpressionContext context)
            throws ParsingException {
        RValue l = i.getNextExpression(context);
        RValue low = BasicType.Integer.convert(l, context);
        if (low == null) {
            throw new NonIntegerIndexException(l);
        }
        Object min = low.compileTimeValue(context);
        if (min == null) {
            throw new NonConstantExpressionException(low);
        }
        lower = (int) min;

        Token t = i.take();
        if (!(t instanceof DotDotToken)) {
            throw new ExpectedTokenException("..", t);
        }
        RValue h = i.getNextExpression(context);
        RValue high = BasicType.Integer.convert(h, context);
        if (high == null) {
            throw new NonIntegerIndexException(h);
        }
        Object max = high.compileTimeValue(context);
        if (max == null) {
            throw new NonConstantExpressionException(high);
        }
        size = (((int) max) - lower) + 1;
    }

    public SubrangeType(int lower, int size) {
        this.lower = lower;
        this.size = size;
    }

    public int lower;
    public int size;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + lower;
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SubrangeType))
            return false;
        SubrangeType other = (SubrangeType) obj;
        return lower == other.lower && size == other.size;
    }

    public boolean contains(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SubrangeType))
            return false;
        SubrangeType other = (SubrangeType) obj;
        return lower <= other.lower
                && (lower + size) >= (other.lower + other.size);
    }

    @Override
    public String toString() {
        return lower + ".." + (lower + size - 1);
    }
}
