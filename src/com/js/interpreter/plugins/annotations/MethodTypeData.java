package com.js.interpreter.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// Make this annotation accessible at runtime via reflection.
@Target({ElementType.METHOD})
public @interface MethodTypeData {
    public ArrayBoundsInfo[] info();
}
