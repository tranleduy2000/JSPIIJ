package com.js.interpreter.plugins;

import java.io.PrintStream;
import java.util.Map;

import com.js.interpreter.ast.PascalPlugin;

public class IO_plugins implements PascalPlugin {
	PrintStream stdout;

	public IO_plugins() {
		this.stdout = System.out;
	}

	public IO_plugins(Map<String, Object> arguments) {
		this.stdout = (PrintStream) arguments.get("stdout");
		if (stdout == null) {
			stdout = System.out;
		}
	}

	/**
	 * Redirects to System.out
	 * 
	 * @param s
	 *            The string to output.
	 */
	public void writeln(Object... values) {
		write(values);
		stdout.println();
	}

	public void write(Object... values) {
		StringBuilder result = new StringBuilder();
		for (Object o : values) {
			result.append(o);
		}
		stdout.print(result);
	}

	public void printf(String format, Object... args) {
		stdout.printf(format,  args);
	}
}
