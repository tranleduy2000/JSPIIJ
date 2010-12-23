package com.js.interpreter.ast.instructions;

import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

public class ReturnInstruction implements Executable {
LineInfo line;
public ReturnInstruction(LineInfo line) {
	this.line=line;
}
@Override
	public LineInfo getLineNumber() {
		return line;
	}
	@Override
	public ExecutionResult execute(VariableContext f,RuntimeExecutable<?> main) throws RuntimePascalException {
		return ExecutionResult.RETURN;
	}

}
