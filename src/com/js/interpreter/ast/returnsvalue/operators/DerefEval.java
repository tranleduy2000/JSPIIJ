package com.js.interpreter.ast.returnsvalue.operators;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.ConstantAccess;
import com.js.interpreter.ast.returnsvalue.DebuggableLValue;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.ConstantCalculationException;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.PointerType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.Reference;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

public class DerefEval extends DebuggableLValue {
    RValue pointer;
    LineInfo line;

    public DerefEval(RValue pointer, LineInfo line) {
        this.pointer = pointer;
        this.line = line;
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main) throws RuntimePascalException {
        Reference ref = (Reference) pointer.getValue(f, main);
        return ref.get();
    }

    @Override
    public Reference<?> getReferenceImpl(VariableContext f, RuntimeExecutable<?> main) throws RuntimePascalException {
        return (Reference) pointer.getValue(f, main);
    }

    @Override
    public RuntimeType get_type(ExpressionContext f) throws ParsingException {
        RuntimeType pointertype = pointer.get_type(f);
        return new RuntimeType(((PointerType) pointertype.declType).pointedToType, true);
    }

    @Override
    public LineInfo getLineNumber() {
        return line;
    }

    @Override
    public Object compileTimeValue(CompileTimeContext context) throws ParsingException {
        Reference<?> ref = (Reference<?>) pointer.compileTimeValue(context);
        if (ref != null) {
            try {
                return ref.get();
            } catch (RuntimePascalException e) {
                throw new ConstantCalculationException(e);
            }
        }

        return null;
    }


    @Override
    public RValue compileTimeExpressionFold(CompileTimeContext context) throws ParsingException {
        Object val = this.compileTimeValue(context);
        if (val != null) {
            return new ConstantAccess(val, line);
        } else {
            return new DerefEval(pointer.compileTimeExpressionFold(context), line);
        }
    }
}
