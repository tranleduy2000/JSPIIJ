package com.js.interpreter.plugins.templated;

import com.js.interpreter.ast.AbstractFunction;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.FunctionCall;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.ArgumentType;
import com.js.interpreter.pascaltypes.DeclaredType;

import java.util.List;

public class TemplatedPluginDeclaration extends AbstractFunction {
    TemplatedPascalPlugin t;

    public TemplatedPluginDeclaration(TemplatedPascalPlugin t) {
        this.t = t;
    }

    @Override
    public LineInfo getLineNumber() {
        return new LineInfo(-1, t.getClass().getCanonicalName());
    }

    @Override
    public String getEntityType() {
        return "Templated Plugin";
    }

    @Override
    public String name() {
        return t.name();
    }

    @Override
    public ArgumentType[] argumentTypes() {
        return t.argumentTypes();
    }

    @Override
    public DeclaredType return_type() {
        return t.return_type();
    }

    @Override
    public FunctionCall generatePerfectFitCall(LineInfo line,
                                               List<RValue> values, ExpressionContext f)
            throws ParsingException {
        RValue[] args = this.perfectMatch(values, f);
        if (args == null) {
            return null;
        }
        return t.generatePerfectFitCall(line, args, f);
    }

    @Override
    public FunctionCall generateCall(LineInfo line, List<RValue> values,
                                     ExpressionContext f) throws ParsingException {
        RValue[] args = this.format_args(values, f);
        if (args == null) {
            return null;
        }
        return t.generateCall(line, args, f);
    }

}
