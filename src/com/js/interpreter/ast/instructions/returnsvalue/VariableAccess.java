package com.js.interpreter.ast.instructions.returnsvalue;

import com.js.interpreter.ast.FunctionDeclaration;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;
import com.js.interpreter.runtime.variables.VariableIdentifier;

public class VariableAccess implements ReturnsValue {
	public VariableIdentifier variable_name;

	public VariableAccess(VariableIdentifier name) {
		this.variable_name = name;
	}

	public Object get_value(VariableContext f, RuntimeExecutable<?> main) throws RuntimePascalException {
		return variable_name.get_value(f, main);
	}

	@Override
	public String toString() {
		return variable_name.toString();
	}

	public RuntimeType get_type(FunctionDeclaration f) throws ParsingException{
		return variable_name.get_type(f);
	}
}
