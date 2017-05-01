package com.js.interpreter.ast.returnsvalue.boxing;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.ast.returnsvalue.ConstantAccess;
import com.js.interpreter.ast.returnsvalue.DebuggableRValue;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.BasicType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

public class CharacterBoxer extends DebuggableRValue {
    RValue c;

    public CharacterBoxer(RValue c) {
        this.c = c;
    }

    @Override
    public LineInfo getLineNumber() {
        return c.getLineNumber();
    }

    @Override
    public RuntimeType get_type(ExpressionContext f) {
        return new RuntimeType(BasicType.StringBuilder, false);
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
            throws RuntimePascalException {
        return new StringBuilder(c.getValue(f, main).toString());
    }

    @Override
    public Object compileTimeValue(CompileTimeContext context)
            throws ParsingException {
        Object val = c.compileTimeValue(context);
        if (val != null) {
            return new StringBuilder(val.toString());
        } else {
            return null;
        }
    }


    @Override
    public RValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        Object val = this.compileTimeValue(context);
        if (val != null) {
            return new ConstantAccess(val, c.getLineNumber());
        } else {
            return new CharacterBoxer(c.compileTimeExpressionFold(context));
        }
    }

}
