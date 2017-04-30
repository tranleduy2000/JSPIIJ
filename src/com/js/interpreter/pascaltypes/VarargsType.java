package com.js.interpreter.pascaltypes;

import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.ast.returnsvalue.boxing.ArrayBoxer;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VarargsType implements ArgumentType {
    RuntimeType elementType;

    public VarargsType(RuntimeType elementType) {
        this.elementType = elementType;
    }

    @Override
    public RValue convertArgType(Iterator<RValue> args,
                                 ExpressionContext f) throws ParsingException {
        List<RValue> convertedargs = new ArrayList<RValue>();
        LineInfo line = null;
        while (args.hasNext()) {
            RValue tmp = elementType.convert(args.next(), f);
            if (tmp == null) {
                return null;
            }
            line = tmp.getLineNumber();
            convertedargs.add(tmp);
        }
        return new ArrayBoxer(
                convertedargs.toArray(new RValue[convertedargs.size()]),
                elementType, line);
    }

    @Override
    public Class getRuntimeClass() {
        return elementType.getClass();
    }

    @Override
    public RValue perfectFit(Iterator<RValue> types,
                             ExpressionContext e) throws ParsingException {
        LineInfo line = null;
        List<RValue> converted = new ArrayList<RValue>();
        while (types.hasNext()) {
            RValue fit = elementType.perfectFit(types, e);
            if (fit == null) {
                return null;
            }
            if (line == null) {
                line = fit.getLineNumber();
            }
            converted.add(fit);
        }
        RValue[] convert = converted.toArray(new RValue[converted
                .size()]);
        return new ArrayBoxer(convert, elementType, line);
    }
}
