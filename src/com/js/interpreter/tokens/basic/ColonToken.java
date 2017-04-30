package com.js.interpreter.tokens.basic;

import com.js.interpreter.linenumber.LineInfo;

public class ColonToken extends BasicToken {
    public ColonToken(LineInfo line) {
        super(line);
    }

    @Override
    public String toString() {
        return ":";
    }
}
