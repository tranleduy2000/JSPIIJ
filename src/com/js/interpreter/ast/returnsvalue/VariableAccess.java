package com.js.interpreter.ast.returnsvalue;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.instructions.FieldReference;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.ast.instructions.Assignment;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.Reference;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;
import com.js.interpreter.tokens.WordToken;

public class VariableAccess extends DebuggableLValue {
    public String name;
    LineInfo line;

    public VariableAccess(WordToken t) {
        this.name = t.name;
        this.line = t.lineInfo;
    }

    public VariableAccess(String name, LineInfo line) {
        this.name = name;
        this.line = line;
    }

    @Override
    public LineInfo getLineNumber() {
        return line;
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
            throws RuntimePascalException {
        return f.get_var(name);
    }

    @Override
    public Reference<?> getReferenceImpl(VariableContext f, RuntimeExecutable<?> main) throws RuntimePascalException {
        return new FieldReference(f, name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public RuntimeType get_type(ExpressionContext f) throws ParsingException {
        return new RuntimeType(f.getVariableDefinition(name).type, true);
    }

    @Override
    public Object compileTimeValue(CompileTimeContext context)
            throws ParsingException {
        return null;
    }

    @Override
    public RValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        return this;
    }
}
