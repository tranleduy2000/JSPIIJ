package com.js.interpreter.ast.returnsvalue.operators;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.ConstantAccess;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.BasicType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.PascalArithmeticException;
import com.js.interpreter.runtime.exception.RuntimePascalException;
import com.js.interpreter.runtime.exception.internal.InternalInterpreterException;
import com.js.interpreter.tokens.OperatorTypes;

public class BoolBiOperatorEval extends BinaryOperatorEvaluation {

    public BoolBiOperatorEval(RValue operon1, RValue operon2,
                              OperatorTypes operator, LineInfo line) {
        super(operon1, operon2, operator, line);
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
            throws RuntimePascalException {
        boolean value1 = (Boolean) operon1.getValue(f, main);
        if ((operator_type == OperatorTypes.AND && !value1) || (operator_type == OperatorTypes.OR && value1)) {
            return value1;
        }
        Boolean value2 = (Boolean) operon2.getValue(f, main);
        return operate(value1, value2);
    }

    @Override
    public RuntimeType get_type(ExpressionContext f) throws ParsingException {
        return new RuntimeType(BasicType.Boolean, false);
    }

    @Override
    public Object operate(Object value1, Object value2)
            throws PascalArithmeticException, InternalInterpreterException {
        boolean v1 = (Boolean) value1;
        boolean v2 = (Boolean) value2;
        switch (operator_type) {
            case AND:
                return v1 & v2;
            case EQUALS:
                return v1 == v2;
            case NOTEQUAL:
                return v1 != v2;
            case OR:
                return v1 | v2;
            case XOR:
                return v1 ^ v2;
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
            return new BoolBiOperatorEval(
                    operon1.compileTimeExpressionFold(context),
                    operon2.compileTimeExpressionFold(context), operator_type,
                    line);
        }
    }
}
