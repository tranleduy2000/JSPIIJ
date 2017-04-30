package com.js.interpreter.pascaltypes.typeconversion;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.ast.returnsvalue.LValue;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.exceptions.UnconvertibleTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.BasicType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

import java.util.HashMap;

public class TypeConverter {
    static HashMap<Class, Integer> precedence = new HashMap<Class, Integer>();

    static {
        precedence.put(Character.class, 0);
        precedence.put(Integer.class, 1);
        precedence.put(Long.class, 2);
        precedence.put(Double.class, 3);
    }

    public static RValue autoConvert(BasicType outtype,
                                     RValue target, BasicType intype) {
        if (intype == outtype) {
            return target;
        }
        Integer inprecedence = precedence.get(intype.getTransferClass());
        Integer outprecedence = precedence.get(outtype.getTransferClass());
        if (inprecedence != null && outprecedence != null) {
            if (inprecedence < outprecedence) {
                return forceConvert(outtype, target, intype);
            }
        }
        if (outtype == BasicType.StringBuilder && intype == BasicType.Character) {
            return forceConvert(outtype, target, intype);
        }
        return null;
    }

    public static RValue autoConvertRequired(BasicType outtype,
                                             RValue target, BasicType intype)
            throws UnconvertibleTypeException {
        RValue result = autoConvert(outtype, target, intype);
        if (result == null) {
            throw new UnconvertibleTypeException(target, outtype, intype, true);
        }
        return result;
    }

    public static RValue forceConvertRequired(BasicType outtype,
                                              RValue target, BasicType intype)
            throws UnconvertibleTypeException {
        RValue result = forceConvert(outtype, target, intype);
        if (result == null) {
            throw new UnconvertibleTypeException(target, outtype, intype, false);
        }
        return result;
    }

    public static RValue forceConvert(BasicType outtype,
                                      RValue target, BasicType intype) {
        if (outtype == intype) {
            return target;
        }
        if (outtype.equals(BasicType.StringBuilder)) {
            return new AnyToString(target);
        }
        if (intype == BasicType.Character) {
            target = new CharToInt(target);
            if (outtype == BasicType.Integer) {
                return target;
            } else if (outtype == BasicType.Long) {
                return new NumberToLong(target);
            } else if (outtype == BasicType.Double) {
                return new NumberToReal(target);
            }
        }
        if (intype == BasicType.Integer) {
            if (outtype == BasicType.Character) {
                return new NumberToChar(target);
            } else if (outtype == BasicType.Long) {
                return new NumberToLong(target);
            } else if (outtype == BasicType.Double) {
                return new NumberToReal(target);
            }
        }
        if (intype == BasicType.Long) {
            if (outtype == BasicType.Character) {
                return new NumberToChar(target);
            } else if (outtype == BasicType.Integer) {
                return new NumberToInt(target);
            } else if (outtype == BasicType.Double) {
                return new NumberToReal(target);
            }
        }
        if (intype == BasicType.Double) {
            if (outtype == BasicType.Character) {
                return new NumberToChar(target);
            } else if (outtype == BasicType.Integer) {
                return new NumberToInt(target);
            } else if (outtype == BasicType.Long) {
                return new NumberToLong(target);
            }
        }
        return null;
    }

    static class NumberToReal implements RValue {
        RValue other;

        public NumberToReal(RValue other) {
            this.other = other;
        }

        @Override
        public Object getValue(VariableContext f, RuntimeExecutable<?> main)
                throws RuntimePascalException {
            Number i = (Number) other.getValue(f, main);
            return i.doubleValue();
        }

        @Override
        public RuntimeType get_type(ExpressionContext f)
                throws ParsingException {
            return new RuntimeType(BasicType.Double, false);
        }

        @Override
        public LineInfo getLineNumber() {
            return other.getLineNumber();
        }

        @Override
        public Object compileTimeValue(CompileTimeContext context)
                throws ParsingException {
            Object o = other.compileTimeValue(context);
            if (o != null) {
                return ((Number) o).doubleValue();
            } else {
                return null;
            }
        }


        @Override
        public RValue compileTimeExpressionFold(CompileTimeContext context)
                throws ParsingException {
            return new NumberToReal(other.compileTimeExpressionFold(context));
        }

        @Override
        public LValue asLValue(ExpressionContext f) {
            return null;
        }
    }

    static class NumberToLong implements RValue {
        RValue other;

        public NumberToLong(RValue other) {
            this.other = other;
        }

        @Override
        public Object getValue(VariableContext f, RuntimeExecutable<?> main)
                throws RuntimePascalException {
            Number i = (Number) other.getValue(f, main);
            return i.longValue();
        }

        @Override
        public RuntimeType get_type(ExpressionContext f)
                throws ParsingException {
            return new RuntimeType(BasicType.Long, false);
        }

        @Override
        public LineInfo getLineNumber() {
            return other.getLineNumber();
        }

        @Override
        public Object compileTimeValue(CompileTimeContext context)
                throws ParsingException {
            Object o = other.compileTimeValue(context);
            if (o != null) {
                return ((Number) o).longValue();
            } else {
                return null;
            }
        }


        @Override
        public RValue compileTimeExpressionFold(CompileTimeContext context)
                throws ParsingException {
            return new NumberToLong(other.compileTimeExpressionFold(context));
        }

        @Override
        public LValue asLValue(ExpressionContext f) {
            return null;
        }
    }

    static class NumberToChar implements RValue {
        RValue other;

        public NumberToChar(RValue other) {
            this.other = other;
        }

        @Override
        public Object getValue(VariableContext f, RuntimeExecutable<?> main)
                throws RuntimePascalException {
            Number i = (Number) other.getValue(f, main);
            return (char) i.longValue();
        }

        @Override
        public RuntimeType get_type(ExpressionContext f)
                throws ParsingException {
            return new RuntimeType(BasicType.Character, false);
        }

        @Override
        public LineInfo getLineNumber() {
            return other.getLineNumber();
        }

        @Override
        public Object compileTimeValue(CompileTimeContext context)
                throws ParsingException {
            Object o = other.compileTimeValue(context);
            if (o != null) {
                return (char) ((Number) o).longValue();
            } else {
                return null;
            }
        }


        @Override
        public RValue compileTimeExpressionFold(CompileTimeContext context)
                throws ParsingException {
            return new NumberToChar(other.compileTimeExpressionFold(context));
        }

        @Override
        public LValue asLValue(ExpressionContext f) {
            return null;
        }
    }

    static class NumberToInt implements RValue {
        RValue other;

        public NumberToInt(RValue other) {
            this.other = other;
        }

        @Override
        public Object getValue(VariableContext f, RuntimeExecutable<?> main)
                throws RuntimePascalException {
            Number i = (Number) other.getValue(f, main);
            return i.intValue();
        }

        @Override
        public RuntimeType get_type(ExpressionContext f)
                throws ParsingException {
            return new RuntimeType(BasicType.Integer, false);
        }

        @Override
        public LineInfo getLineNumber() {
            return other.getLineNumber();
        }

        @Override
        public Object compileTimeValue(CompileTimeContext context)
                throws ParsingException {
            Object o = other.compileTimeValue(context);
            if (o != null) {
                return ((Number) o).intValue();
            } else {
                return null;
            }
        }

        @Override
        public RValue compileTimeExpressionFold(CompileTimeContext context)
                throws ParsingException {
            return new NumberToInt(other.compileTimeExpressionFold(context));
        }

        @Override
        public LValue asLValue(ExpressionContext f) {
            return null;
        }
    }

    static class CharToInt implements RValue {
        RValue other;

        public CharToInt(RValue other) {
            this.other = other;
        }

        @Override
        public Object getValue(VariableContext f, RuntimeExecutable<?> main)
                throws RuntimePascalException {
            Character i = (Character) other.getValue(f, main);
            return (int) i.charValue();
        }

        @Override
        public RuntimeType get_type(ExpressionContext f)
                throws ParsingException {
            return new RuntimeType(BasicType.Integer, false);
        }

        @Override
        public LineInfo getLineNumber() {
            return other.getLineNumber();
        }

        @Override
        public Object compileTimeValue(CompileTimeContext context)
                throws ParsingException {
            Object o = other.compileTimeValue(context);
            if (o != null) {
                return (int) ((Character) o).charValue();
            } else {
                return null;
            }
        }


        @Override
        public RValue compileTimeExpressionFold(CompileTimeContext context)
                throws ParsingException {
            return new CharToInt(other.compileTimeExpressionFold(context));
        }

        @Override
        public LValue asLValue(ExpressionContext f) {
            return null;
        }
    }

    public static class AnyToString implements RValue {
        RValue other;

        public AnyToString(RValue other) {
            this.other = other;
        }

        @Override
        public Object getValue(VariableContext f, RuntimeExecutable<?> main)
                throws RuntimePascalException {
            return other.getValue(f, main).toString();
        }

        @Override
        public RuntimeType get_type(ExpressionContext f)
                throws ParsingException {
            return new RuntimeType(BasicType.create(String.class), false);
        }

        @Override
        public LineInfo getLineNumber() {
            return other.getLineNumber();
        }

        @Override
        public Object compileTimeValue(CompileTimeContext context)
                throws ParsingException {
            Object o = other.compileTimeValue(context);
            if (o != null) {
                return o.toString();
            } else {
                return null;
            }
        }


        @Override
        public RValue compileTimeExpressionFold(CompileTimeContext context)
                throws ParsingException {
            return new AnyToString(other.compileTimeExpressionFold(context));
        }

        @Override
        public LValue asLValue(ExpressionContext f) {
            return null;
        }
    }
}
