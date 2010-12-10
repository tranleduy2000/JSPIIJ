package com.js.interpreter.ast.instructions.returnsvalue;

import com.js.interpreter.ast.FunctionDeclaration;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;

public interface ReturnsValue {
	public abstract Object get_value(VariableContext f,
			RuntimeExecutable<?> main);

	public abstract RuntimeType get_type(FunctionDeclaration f)
			throws ParsingException;
}
