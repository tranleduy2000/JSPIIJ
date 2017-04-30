package com.js.interpreter.pascaltypes;

import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.RValue;
import com.js.interpreter.ast.returnsvalue.StringIndex;
import com.js.interpreter.ast.returnsvalue.StringIndexReference;
import com.js.interpreter.ast.returnsvalue.boxing.CharacterBoxer;
import com.js.interpreter.ast.returnsvalue.boxing.StringBoxer;
import com.js.interpreter.ast.returnsvalue.cloning.StringBuilderCloner;
import com.js.interpreter.exceptions.NonArrayIndexed;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.pascaltypes.bytecode.RegisterAllocator;
import com.js.interpreter.pascaltypes.bytecode.TransformationInput;
import com.js.interpreter.pascaltypes.typeconversion.TypeConverter;
import ncsa.tools.common.util.TypeUtils;
import serp.bytecode.Code;

import java.util.List;

public enum BasicType implements DeclaredType {
    Boolean(Character.class) {
        @Override
        Object getDefaultValue() {
            return false;
        }

        @Override
        public String toString() {
            return "Boolean";
        }

        @Override
        public void convertStackToStorageType(Code c) {
            c.invokestatic().setMethod(Boolean.class, "valueOf", Boolean.class,
                    new Class[]{boolean.class});
        }

        @Override
        public void arrayStoreOperation(Code c) {
            c.bastore();
        }
    },
    Character(Character.class) {
        @Override
        Object getDefaultValue() {
            return '\0';
        }

        @Override
        public String toString() {
            return "Character";
        }

        @Override
        public void convertStackToStorageType(Code c) {
            c.invokestatic().setMethod(Character.class, "valueOf",
                    Character.class, new Class[]{char.class});
        }

        @Override
        public void arrayStoreOperation(Code c) {
            c.castore();
        }
    },
    StringBuilder(StringBuilder.class) {
        @Override
        Object getDefaultValue() {
            return new StringBuilder();
        }

        @Override
        public void pushDefaultValue(Code constructor_code, RegisterAllocator ra) {
            constructor_code.anew().setType(StringBuilder.class);
            constructor_code.dup();
            try {
                constructor_code.invokespecial().setMethod(
                        StringBuilder.class.getConstructor());
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "String";
        }

        @Override
        public RValue convert(RValue value, ExpressionContext f)
                throws ParsingException {
            RuntimeType other_type = value.get_type(f);
            if (other_type.declType instanceof BasicType) {

                if (this.equals(other_type.declType)) {
                    return value;
                }
                if (other_type.declType == BasicType.Character) {
                    return new CharacterBoxer(value);
                }
                if (((BasicType) other_type.declType).c == String.class) {
                    return new StringBoxer(value);
                }

                return TypeConverter.autoConvert(this, value,
                        (BasicType) other_type.declType);
            }
            return null;
        }

        @Override
        public RValue generateArrayAccess(RValue array,
                                          RValue index) throws NonArrayIndexed {
            return new StringIndex(array, index);
        }

        @Override
        public RValue cloneValue(RValue r) {
            return new StringBuilderCloner(r);
        }

        @Override
        public void cloneValueOnStack(TransformationInput t) {
            Code c = t.getCode();
            c.anew().setType(StringBuilder.class);
            t.pushInputOnStack();
            try {
                c.invokespecial().setMethod(
                        StringBuilder.class.getConstructor(CharSequence.class));
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    },
    Long(Long.class) {
        @Override
        Object getDefaultValue() {
            return 0L;
        }

        @Override
        public String toString() {
            return "Long";
        }
    },
    Double(Double.class) {
        @Override
        Object getDefaultValue() {
            return 0.0D;
        }

        @Override
        public String toString() {
            return "Double";
        }

        @Override
        public void convertStackToStorageType(Code c) {
            c.invokestatic().setMethod(Double.class, "valueOf", Double.class,
                    new Class[]{double.class});
        }

        @Override
        public void arrayStoreOperation(Code c) {
            c.dastore();
        }
    },
    Integer(Integer.class) {
        @Override
        Object getDefaultValue() {
            return (int) 0;
        }

        @Override
        public String toString() {
            return "Integer";
        }

        @Override
        public void convertStackToStorageType(Code c) {
            c.invokestatic().setMethod(Integer.class, "valueOf", Integer.class,
                    new Class[]{int.class});
        }

        @Override
        public void arrayStoreOperation(Code c) {
            c.iastore();
        }
    };
    private Class c;

    abstract Object getDefaultValue();

    private BasicType(Class name) {
        c = name;
    }

    @Override
    public boolean equals(DeclaredType obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JavaClassBasedType) {
            Class other = ((JavaClassBasedType) obj).c;
            return c == other || c == Object.class || other == Object.class;
        }
        return false;
    }

    @Override
    public Object initialize() {
        Object result;
        if ((result = getDefaultValue()) != null) {
            return result;
        } else {
            try {
                return c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public Class getTransferClass() {
        return c;
    }

    @Override
    public abstract String toString();

    public static DeclaredType create(Class c) {
        if (c == Integer.class) {
            return Integer;
        }
        if (c == Double.class) {
            return Double;
        }
        if (c == StringBuilder.class) {
            return StringBuilder;
        }
        if (c == Long.class) {
            return Long;
        }
        if (c == Character.class) {
            return Character;
        }
        if (c == Boolean.class) {
            return Boolean;
        }
        return new JavaClassBasedType(c);
    }

    @Override
    public RValue convert(RValue value, ExpressionContext f)
            throws ParsingException {
        RuntimeType other_type = value.get_type(f);
        if (other_type.declType instanceof BasicType) {
            if (this.equals(other_type.declType)) {
                return cloneValue(value);
            }
            return TypeConverter.autoConvert(this, value,
                    (BasicType) other_type.declType);
        }
        return null;
    }

    @Override
    public void pushDefaultValue(Code constructor_code, RegisterAllocator ra) {
        constructor_code.constant().setValue(getDefaultValue());
    }

    @Override
    public RValue cloneValue(final RValue r) {
        return r;
    }

    @Override
    public void cloneValueOnStack(TransformationInput t) {
        t.pushInputOnStack();
    }

    @Override
    public RValue generateArrayAccess(RValue array,
                                      RValue index) throws NonArrayIndexed {
        throw new NonArrayIndexed(array.getLineNumber(), this);
    }

    @Override
    public Class<?> getStorageClass() {
        Class c2 = TypeUtils.getTypeForClass(c);
        return c2 == null ? c : c2;
    }

    @Override
    public void arrayStoreOperation(Code c) {
        c.aastore();
    }

    @Override
    public void convertStackToStorageType(Code c) {
        // By default, nothing is necessary.
    }

    @Override
    public void pushArrayOfType(Code code, RegisterAllocator ra,
                                List<SubrangeType> ranges) {
        // Because I cannot mix this method into DeclaredType (no multiple
        // inheritance) I have to duplicate it.
        ArrayType.pushArrayOfNonArrayType(this, code, ra, ranges);

    }
}
