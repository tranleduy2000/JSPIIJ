package com.js.interpreter.plugins.standard;

import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.FunctionCall;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.ArgumentType;
import com.js.interpreter.pascaltypes.DeclaredType;
import com.js.interpreter.plugins.templated.TemplatedPascalPlugin;

public class SetArrayLength implements TemplatedPascalPlugin {

    SetLength a = new SetLength();

    @Override
    public String name() {
        return a.name();
    }

    @Override
    public FunctionCall generateCall(LineInfo line, RValue[] values,
                                     ExpressionContext f) throws ParsingException {
        return a.generateCall(line, values, f);
    }

    @Override
    public FunctionCall generatePerfectFitCall(LineInfo line,
                                               RValue[] values, ExpressionContext f) throws ParsingException {
        return a.generatePerfectFitCall(line, values, f);
    }

    @Override
    public ArgumentType[] argumentTypes() {
        return a.argumentTypes();
    }

    @Override
    public DeclaredType return_type() {
        return a.return_type();
    }

}
