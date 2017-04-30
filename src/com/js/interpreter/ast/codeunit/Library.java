package com.js.interpreter.ast.codeunit;

import com.google.common.collect.ListMultimap;
import com.js.interpreter.ast.AbstractFunction;
import com.js.interpreter.exceptions.MisplacedDeclarationException;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.runtime.codeunit.RuntimeLibrary;
import com.js.interpreter.startup.ScriptSource;
import com.js.interpreter.tokens.grouping.GrouperToken;

import java.io.Reader;
import java.util.List;

public class Library extends CodeUnit {
    public Library(ListMultimap<String, AbstractFunction> functionTable)
            throws ParsingException {
        super(functionTable);
    }

    @Override
    protected LibraryExpressionContext getExpressionContextInstance(


            ListMultimap<String, AbstractFunction> f) {
        return new LibraryExpressionContext(f);
    }

    protected class LibraryExpressionContext extends CodeUnitExpressionContext {

        protected LibraryExpressionContext(
                ListMultimap<String, AbstractFunction> function) {
            super(function);
        }

        @Override
        public void handleBeginEnd(GrouperToken i) throws ParsingException {
            throw new MisplacedDeclarationException(i.peek().lineInfo,
                    "main function", Library.this.context);
        }
    }

    public Library(Reader program,
                   ListMultimap<String, AbstractFunction> functionTable,
                   String sourcename, List<ScriptSource> includeDirectories)
            throws ParsingException {
        super(program, functionTable, sourcename, includeDirectories);
    }

    @Override
    public RuntimeLibrary run() {
        return new RuntimeLibrary(this);
    }

}
