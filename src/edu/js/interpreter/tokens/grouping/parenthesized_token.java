package edu.js.interpreter.tokens.grouping;

import edu.js.interpreter.tokens.token;

public class parenthesized_token extends grouper_token {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3945938644412769985L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("(");
		if (next != null) {
			builder.append(next).append(',');
		}
		for (token t : this.queue) { 
			builder.append(t).append(' ');
		}
		builder.append(')');
		return builder.toString();
	}
}
