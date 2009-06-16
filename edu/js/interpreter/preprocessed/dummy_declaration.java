package edu.js.interpreter.preprocessed;

import javax.naming.OperationNotSupportedException;

import edu.js.interpreter.pascal_types.pascal_type;
import edu.js.interpreter.processing.pascal_program;


public class dummy_declaration extends abstract_function {
	String name;

	pascal_type[] arg_types;

	public dummy_declaration(String name, pascal_type[] arg_types) {
		this.name = name;
		this.arg_types = arg_types;
	}

	@Override
	public pascal_type[] get_arg_types() {
		return arg_types;
	}

	@Override
	public String get_name() {
		return name;
	}

	@Override
	public pascal_type get_return_type() {
		new OperationNotSupportedException(
				"Dummy Declarations Do Not Have a Return Type")
				.printStackTrace();
		return null;
	}

	@Override
	public Object call(pascal_program program, Object[] arguments) {
		return null;
	}

	@Override
	public boolean is_varargs(int i) {
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + ",dummy";
	}
}