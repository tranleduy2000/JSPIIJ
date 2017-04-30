package com.js.interpreter.exceptions;

import com.js.interpreter.linenumber.LineInfo;

public class UnrecognizedTypeException extends ParsingException {

    public UnrecognizedTypeException(LineInfo line, String name) {
        super(line, "Type " + name + " is not defined");
    }

}
