package com.js.interpreter.ast.returnsvalue.operators;

import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.ast.returnsvalue.DebuggableRValue;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.exceptions.BadOperationTypeException;
import com.js.interpreter.exceptions.ConstantCalculationException;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.BasicType;
import com.js.interpreter.pascaltypes.DeclaredType;
import com.js.interpreter.pascaltypes.JavaClassBasedType;
import com.js.interpreter.pascaltypes.typeconversion.TypeConverter;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.PascalArithmeticException;
import com.js.interpreter.runtime.exception.RuntimePascalException;
import com.js.interpreter.runtime.exception.internal.InternalInterpreterException;
import com.js.interpreter.tokens.OperatorTypes;

public abstract class BinaryOperatorEvaluation extends DebuggableRValue {
    OperatorTypes operator_type;

    RValue operon1;

    RValue operon2;
    LineInfo line;

    public BinaryOperatorEvaluation(RValue operon1, RValue operon2,
                                    OperatorTypes operator, LineInfo line) {
        this.operator_type = operator;
        this.operon1 = operon1;
        this.operon2 = operon2;
        this.line = line;
    }

    @Override
    public LineInfo getLineNumber() {
        return line;
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
            throws RuntimePascalException {
        Object value1 = operon1.getValue(f, main);
        Object value2 = operon2.getValue(f, main);
        return operate(value1, value2);
    }

    public abstract Object operate(Object value1, Object value2)
            throws PascalArithmeticException, InternalInterpreterException;

    @Override
    public String toString() {
        return "(" + operon1 + ") " + operator_type + " (" + operon2 + ')';
    }

    @Override
    public Object compileTimeValue(CompileTimeContext context)
            throws ParsingException {
        Object value1 = operon1.compileTimeValue(context);
        Object value2 = operon2.compileTimeValue(context);
        if (value1 != null && value2 != null) {
            try {
                return operate(value1, value2);
            } catch (PascalArithmeticException e) {
                throw new ConstantCalculationException(e);
            } catch (InternalInterpreterException e) {
                throw new ConstantCalculationException(e);
            }
        } else {
            return null;
        }
    }

    /* Boy, templates or macros like C++ sure would be useful now... */
    public static BinaryOperatorEvaluation generateOp(ExpressionContext f,
                                                      RValue v1, RValue v2, OperatorTypes op_type,
                                                      LineInfo line) throws ParsingException {
        DeclaredType t1 = v1.get_type(f).declType;
        DeclaredType t2 = v2.get_type(f).declType;
        if (!(t1 instanceof BasicType || t1 instanceof JavaClassBasedType)) {
            throw new BadOperationTypeException(line, t1, t2, v1, v2, op_type);
        }
        if (!(t2 instanceof BasicType || t2 instanceof JavaClassBasedType)) {
            throw new BadOperationTypeException(line, t1, t2, v1, v2, op_type);
        }
        if (t1 == BasicType.StringBuilder
                || t2 == BasicType.StringBuilder) {
            if (op_type == OperatorTypes.PLUS) {
                v1 = new TypeConverter.AnyToString(v1);
                v2 = new TypeConverter.AnyToString(v2);
                return new StringBiOperatorEval(v1, v2, op_type, line);
            } else {
                v1 = BasicType.StringBuilder.convert(v1, f);
                v2 = BasicType.StringBuilder.convert(v2, f);
                if (v1 != null && v2 != null) {
                    return new StringBiOperatorEval(v1, v2, op_type, line);
                } else {
                    throw new BadOperationTypeException(line, t1, t2, v1, v2,
                            op_type);
                }
            }
        }
        if (t1 == BasicType.Double || t2 == BasicType.Double) {
            v1 = TypeConverter.forceConvertRequired(BasicType.Double,
                    v1, (BasicType) t1);
            v2 = TypeConverter.forceConvertRequired(BasicType.Double,
                    v2, (BasicType) t2);
            return new DoubleBiOperatorEval(v1, v2, op_type, line);
        }
        if (t1 == BasicType.Character
                || t2 == BasicType.Character) {
            v1 = TypeConverter.forceConvertRequired(
                    BasicType.Character, v1, (BasicType) t1);
            v2 = TypeConverter.forceConvertRequired(
                    BasicType.Character, v2, (BasicType) t2);
            return new CharBiOperatorEval(v1, v2, op_type, line);
        }
        if (t1 == BasicType.Long || t2 == BasicType.Long) {
            v1 = TypeConverter.forceConvertRequired(BasicType.Long,
                    v1, (BasicType) t1);
            v2 = TypeConverter.forceConvertRequired(BasicType.Long,
                    v2, (BasicType) t2);
            return new LongBiOperatorEval(v1, v2, op_type, line);
        }
        if (t1 == BasicType.Integer
                || t2 == BasicType.Integer) {
            v1 = TypeConverter.forceConvertRequired(BasicType.Integer,
                    v1, (BasicType) t1);
            v2 = TypeConverter.forceConvertRequired(BasicType.Integer,
                    v2, (BasicType) t2);
            return new IntBiOperatorEval(v1, v2, op_type, line);
        }

        if (t1 == BasicType.Boolean
                || t2 == BasicType.Boolean) {
            v1 = TypeConverter.forceConvertRequired(BasicType.Boolean,
                    v1, (BasicType) t1);
            v2 = TypeConverter.forceConvertRequired(BasicType.Boolean,
                    v2, (BasicType) t2);
            return new BoolBiOperatorEval(v1, v2, op_type, line);
        }
        throw new BadOperationTypeException(line, t1, t2, v1, v2, op_type);
    }
}
