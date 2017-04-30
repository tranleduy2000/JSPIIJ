package com.js.interpreter.ast.codeunit;

import com.google.common.collect.ListMultimap;
import com.js.interpreter.ast.AbstractFunction;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.startup.ScriptSource;

import java.io.Reader;
import java.util.List;

public abstract class ExecutableCodeUnit extends CodeUnit {

    public ExecutableCodeUnit(
            ListMultimap<String, AbstractFunction> functionTable) {
        super(functionTable);
    }

    public ExecutableCodeUnit(Reader r,
                              ListMultimap<String, AbstractFunction> functionTable,
                              String sourcename, List<ScriptSource> includeDirectories)
            throws ParsingException {
        super(r, functionTable, sourcename, includeDirectories);
    }

    @Override
    public abstract RuntimeExecutable<? extends ExecutableCodeUnit> run();


}
