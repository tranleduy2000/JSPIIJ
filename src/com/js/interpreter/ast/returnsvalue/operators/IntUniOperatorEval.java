package com.js.interpreter.ast.returnsvalue.operators;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.ConstantAccess;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.ast.returnsvalue.UnaryOperatorEvaluation;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.BasicType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.exception.PascalArithmeticException;
import com.js.interpreter.runtime.exception.internal.InternalInterpreterException;
import com.js.interpreter.tokens.OperatorTypes;

public class IntUniOperatorEval extends UnaryOperatorEvaluation {

    public IntUniOperatorEval(RValue operon, OperatorTypes operator, LineInfo line) {
        super(operon, operator, line);
    }

    @Override
    public RuntimeType get_type(ExpressionContext f) throws ParsingException {
        return new RuntimeType(BasicType.Integer, false);
    }

    @Override
    public Object operate(Object value) throws PascalArithmeticException, InternalInterpreterException {
        switch (operator) {
            case PLUS:
                return +(Integer) value;
            case MINUS:
                return -(Integer) value;
            default:
                throw new InternalInterpreterException(line);
        }
    }

    @Override
    public RValue compileTimeExpressionFold(CompileTimeContext context) throws ParsingException {
        Object val = this.compileTimeValue(context);
        if (val != null) {
            return new ConstantAccess(val, line);
        } else {
            return new IntUniOperatorEval(operon.compileTimeExpressionFold(context), operator,
                    line);
        }
    }
}
