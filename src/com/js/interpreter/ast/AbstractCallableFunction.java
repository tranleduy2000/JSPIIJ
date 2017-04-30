package com.js.interpreter.ast;

import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.FunctionCall;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.ast.returnsvalue.SimpleFunctionCall;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractCallableFunction extends AbstractFunction {

    /**
     * This invokes a function call of any operator.
     *
     * @param parentcontext The program context.
     * @param arguments
     * @return The return value of the called function.
     * @throws RuntimePascalException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public abstract Object call(VariableContext parentcontext,
                                RuntimeExecutable<?> main, Object[] arguments)
            throws RuntimePascalException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException;

    @Override
    public FunctionCall generatePerfectFitCall(LineInfo line,
                                               List<RValue> values, ExpressionContext f)
            throws ParsingException {
        RValue[] args = perfectMatch(values, f);
        if (args == null) {
            return null;
        }
        return new SimpleFunctionCall(this, args, line);
    }

    @Override
    public FunctionCall generateCall(LineInfo line, List<RValue> values,
                                     ExpressionContext f) throws ParsingException {
        RValue[] args = format_args(values, f);
        if (args == null) {
            return null;
        }
        return new SimpleFunctionCall(this, args, line);
    }


}
