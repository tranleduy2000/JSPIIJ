package com.js.interpreter.runtime.exception;

import com.js.interpreter.linenumber.LineInfo;

public class ScriptTerminatedException extends RuntimePascalException {

    public ScriptTerminatedException(LineInfo line) {
        super(line);
    }

    @Override
    public String getMessage() {
        return "Script was manually terminated before it could finish executing";
    }
}
