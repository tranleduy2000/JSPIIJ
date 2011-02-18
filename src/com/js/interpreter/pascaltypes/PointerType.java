package com.js.interpreter.pascaltypes;

import serp.bytecode.Code;

import com.js.interpreter.ast.CompileTimeContext;
import com.js.interpreter.ast.ExpressionContext;
import com.js.interpreter.ast.instructions.returnsvalue.ReturnsValue;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.runtime.ObjectBasedPointer;
import com.js.interpreter.runtime.PascalPointer;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

public class PointerType extends DeclaredType {
	DeclaredType pointedToType;

	public PointerType(DeclaredType pointedToType) {
		this.pointedToType = pointedToType;
	}

	@Override
	public ReturnsValue convert(ReturnsValue returnsValue, ExpressionContext f)
			throws ParsingException {
		RuntimeType other = returnsValue.get_type(f);
		if (this.equals(other)) {
			return returnsValue;
		}
		return null;
	}

	@Override
	public Object initialize() {
		return new ObjectBasedPointer(pointedToType.initialize());
	}

	@Override
	public boolean isarray() {
		return false;
	}

	@Override
	public Class<?> toclass() {
		return null;
	}

	@Override
	public boolean equals(DeclaredType obj) {
		if (obj instanceof PointerType) {
			return this.pointedToType.equals(((PointerType) obj).pointedToType);
		}
		return false;
	}

	@Override
	public void pushDefaultValue(Code constructor_code) {
		// TODO Auto-generated method stub

	}

	@Override
	public ReturnsValue cloneValue(final ReturnsValue r) {
		return new ReturnsValue() {

			@Override
			public RuntimeType get_type(ExpressionContext f)
					throws ParsingException {
				return r.get_type(f);
			}

			@Override
			public Object getValue(VariableContext f, RuntimeExecutable<?> main)
					throws RuntimePascalException {
				PascalPointer<?> value = (PascalPointer<?>) r.getValue(f, main);
				return value.clone();
			}

			@Override
			public LineInfo getLineNumber() {
				return r.getLineNumber();
			}

			@Override
			public Object compileTimeValue(CompileTimeContext context)
					throws ParsingException {
				PascalPointer<?> value = (PascalPointer<?>) r
						.compileTimeValue(context);
				return value.clone();
			}
		};
	}
}
