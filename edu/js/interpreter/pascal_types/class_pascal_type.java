package edu.js.interpreter.pascal_types;

import java.util.HashMap;

import ncsa.tools.common.util.TypeUtils;

import serp.bytecode.Code;

public class class_pascal_type extends pascal_type {
	Class c;

	protected static HashMap<pascal_type, Object> default_values = new HashMap<pascal_type, Object>();

	public static pascal_type Boolean = new class_pascal_type(Boolean.class);

	public static pascal_type Character = new class_pascal_type(Character.class);

	public static pascal_type String = new class_pascal_type(String.class);

	public static pascal_type Long = new class_pascal_type(Long.class);

	public static pascal_type Double = new class_pascal_type(Double.class);

	public static pascal_type Integer = new class_pascal_type(Integer.class);
	static {
		default_values.put(class_pascal_type.Integer, 0);
		default_values.put(class_pascal_type.String, "");
		default_values.put(class_pascal_type.Double, 0.0D);
		default_values.put(class_pascal_type.Long, 0L);
		default_values.put(class_pascal_type.Character, '\0');
	}

	private class_pascal_type(Class name) {
		c = name;
	}

	@Override
	public boolean isarray() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof class_pascal_type) {
			return c.equals(((class_pascal_type) obj).c);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (TypeUtils.isPrimitiveWrapper(c) ? TypeUtils.getTypeForClass(c)
				: c).getCanonicalName().hashCode();

	}

	@Override
	public Object initialize() {
		Object result;
		if ((result = class_pascal_type.default_values.get(this)) != null) {
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
	public Class toclass() {
		return c;
	}

	public void get_default_value_on_stack(Code code) {
		Object result;
		if ((result = class_pascal_type.default_values.get(this)) != null) {
			code.constant().setValue(result);
		} else {
			try {
				code.anew().setType(c);
				code.invokespecial().setMethod(c.getConstructor());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return;
		}
	}

	@Override
	public String toString() {
		return "class_pascal_type :" + c.toString();
	}

	public static pascal_type anew(Class c) {
		if (c == Integer.class) {
			return class_pascal_type.Integer;
		}
		if (c == Double.class) {
			return class_pascal_type.Double;
		}
		if (c == String.class) {
			return class_pascal_type.String;
		}
		if (c == Long.class) {
			return class_pascal_type.Long;
		}
		if (c == Character.class) {
			return class_pascal_type.Character;
		}
		if (c == Boolean.class) {
			return class_pascal_type.Boolean;
		}
		return new class_pascal_type(c);
	}
}
