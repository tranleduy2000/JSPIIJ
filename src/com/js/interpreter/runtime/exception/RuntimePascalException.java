package com.js.interpreter.runtime.exception;

import com.js.interpreter.linenumber.LineInfo;

public class RuntimePascalException extends Exception {
    public LineInfo line;

    public RuntimePascalException(LineInfo line) {
        this.line = line;
    }
}
