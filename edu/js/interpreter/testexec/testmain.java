package edu.js.interpreter.testexec;

public class testmain {
	public static void main(String[] args) {
		try {
			Class.forName("[[Ljava.lang.String;");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
