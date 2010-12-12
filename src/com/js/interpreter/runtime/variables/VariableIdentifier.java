package com.js.interpreter.runtime.variables;

import java.util.ArrayList;

import com.js.interpreter.ast.FunctionDeclaration;
import com.js.interpreter.exceptions.NoSuchFunctionOrVariableException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.DeclaredType;
import com.js.interpreter.pascaltypes.JavaClassBasedType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;

public class VariableIdentifier extends ArrayList<SubvarIdentifier> {
	LineInfo lineinfo;

	public VariableIdentifier(LineInfo info) {
		super();
		lineinfo = info;
	}

	public RuntimeType get_type(FunctionDeclaration f)
			throws NoSuchFunctionOrVariableException {
		String ident = get(0).toString();
		DeclaredType type = f.get_variable_type(ident);
		if (type == null) {
			Object value = f.program.constants.get(ident);
			if (value != null) {
				type = JavaClassBasedType.anew(value.getClass());
			}

		}
		if (type == null) {
			type = f.program.getGlobalVarType(ident);
		}
		if (type == null) {
			throw new NoSuchFunctionOrVariableException(lineinfo, ident);
		}
		for (int i = 1; i < size(); i++) {
			type = get(i).getType(type);
			if (type == null) {
				throw new NoSuchFunctionOrVariableException(lineinfo, get(i)
						.toString());
			}
		}

		return new RuntimeType(type, true);
	}

	protected void zero_length_check() {
		if (isEmpty()) {
			System.err.println("Error 0 length variable!");
			System.exit(0);
		}
	}

	public Object get_variable_holder(VariableContext context,
			RuntimeExecutable<?> main) {
		Object v = context;
		for (int i = 0; i < size() - 1; i++) {
			SubvarIdentifier index = get(i);
			v = index.get(v, context, main);
		}
		return v;
	}

	public void set_value(VariableContext context, RuntimeExecutable<?> main,
			Object val) {
		zero_length_check();
		Object variable_holder = get_variable_holder(context, main);
		get(size() - 1).set(variable_holder, context, main, val);
	}

	public Object get_value(VariableContext context, RuntimeExecutable<?> main) {
		zero_length_check();
		Object var_holder = get_variable_holder(context, main);
		return get(size() - 1).get(var_holder, context, main);
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.get(0).toString());
		for (int i = 1; i < this.size(); i++) {
			builder.append('.').append(this.get(i));
		}
		return builder.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7674207356042437840L;

}