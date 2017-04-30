package com.js.interpreter.ast.returnsvalue;

import com.js.interpreter.ast.AbstractCallableFunction;
import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.instructions.Executable;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.ArgumentType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.PluginCallException;
import com.js.interpreter.runtime.exception.RuntimePascalException;
import com.js.interpreter.runtime.exception.internal.PluginReflectionException;

import java.lang.reflect.InvocationTargetException;

public class SimpleFunctionCall extends FunctionCall {
    AbstractCallableFunction function;

    LineInfo line;

    public SimpleFunctionCall(AbstractCallableFunction function,
                              RValue[] arguments, LineInfo line) {
        this.function = function;
        if (function == null) {
            System.err.println("Warning: Null function call");
        }
        this.arguments = arguments;
        this.line = line;
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
            throws RuntimePascalException {
        Object[] values = new Object[arguments.length];
        ArgumentType[] argtypes = function.argumentTypes();
        for (int i = 0; i < values.length; i++) {
            values[i] = arguments[i].getValue(f, main);
        }
        Object result;
        try {
            result = function.call(f, main, values);
        } catch (IllegalArgumentException e) {
            throw new PluginReflectionException(line, e);
        } catch (IllegalAccessException e) {
            throw new PluginReflectionException(line, e);
        } catch (InvocationTargetException e) {
            throw new PluginCallException(line, e.getCause(), function);
        }
        return result;
    }

    @Override
    public RuntimeType get_type(ExpressionContext f) {
        return new RuntimeType(function.return_type(), false);
    }

    @Override
    public LineInfo getLineNumber() {
        return line;
    }

    @Override
    protected String getFunctionName() {
        return function.name();
    }

    @Override
    public RValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        return new SimpleFunctionCall(function,
                compileTimeExpressionFoldArguments(context), line);
    }

    @Override
    public Executable compileTimeConstantTransform(CompileTimeContext c)
            throws ParsingException {
        return new SimpleFunctionCall(function,
                compileTimeExpressionFoldArguments(c), line);
    }
}
