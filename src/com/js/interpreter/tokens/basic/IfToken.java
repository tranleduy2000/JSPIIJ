package com.js.interpreter.tokens.basic;

import com.js.interpreter.linenumber.LineInfo;

public class IfToken extends BasicToken {
    public IfToken(LineInfo line) {
        super(line);
    }

    @Override
    public String toString() {
        return "if";
    }
}
