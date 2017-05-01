package com.js.interpreter.ast.returnsvalue;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.BasicType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;

public class ConstantAccess extends DebuggableRValue {
    public Object constant_value;
    final LineInfo line;

    public ConstantAccess(Object o, LineInfo line) {
        this.constant_value = o;
        this.line = line;
    }

    @Override
    public LineInfo getLineNumber() {
        return line;
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main) {
        return constant_value;
    }

    @Override
    public String toString() {
        return constant_value.toString();
    }

    @Override
    public RuntimeType get_type(ExpressionContext f) {
        return new RuntimeType(BasicType.create(constant_value
                .getClass()), false);
    }

    @Override
    public Object compileTimeValue(CompileTimeContext context) {
        return constant_value;
    }

    @Override
    public RValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        return this;
    }

}
