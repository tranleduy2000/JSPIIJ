package com.js.interpreter.ast.codeunit;

import com.google.common.collect.ListMultimap;
import com.js.interpreter.ast.AbstractFunction;
import com.js.interpreter.ast.expressioncontext.ExpressionContextMixin;
import com.js.interpreter.ast.instructions.Executable;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnrecognizedTokenException;
import com.js.interpreter.runtime.codeunit.RuntimeCodeUnit;
import com.js.interpreter.startup.ScriptSource;
import com.js.interpreter.tokenizer.NewLexer;
import com.js.interpreter.tokens.Token;
import com.js.interpreter.tokens.basic.ProgramToken;
import com.js.interpreter.tokens.grouping.GrouperToken;

import java.io.Reader;
import java.util.List;

public abstract class CodeUnit {
    public final ExpressionContextMixin context;

    protected CodeUnitExpressionContext getExpressionContextInstance(
            ListMultimap<String, AbstractFunction> ftable) {
        return new CodeUnitExpressionContext(ftable);
    }

    protected class CodeUnitExpressionContext extends ExpressionContextMixin {
        protected CodeUnitExpressionContext(
                ListMultimap<String, AbstractFunction> function) {
            super(CodeUnit.this, null, function);
        }

        @Override
        protected Executable handleUnrecognizedStatementImpl(Token next,
                                                             GrouperToken container) throws ParsingException {
            throw new UnrecognizedTokenException(next);
        }

        @Override
        protected boolean handleUnrecognizedDeclarationImpl(Token next,
                                                            GrouperToken i) throws ParsingException {
            if (next instanceof ProgramToken) {
                CodeUnit.this.program_name = i.next_word_value();
                i.assert_next_semicolon();
                return true;
            }
            return false;
        }

        @Override
        protected void handleBeginEnd(GrouperToken i) throws ParsingException {
            i.take();
        }

    }

    String program_name;

    public CodeUnit(ListMultimap<String, AbstractFunction> functionTable) {
        prepareForParsing();
        this.context = getExpressionContextInstance(functionTable);
    }

    public CodeUnit(Reader program,
                    ListMultimap<String, AbstractFunction> functionTable,
                    String sourcename, List<ScriptSource> includeDirectories)
            throws ParsingException {
        this(functionTable);
        NewLexer grouper = new NewLexer(program, sourcename, includeDirectories);
        new Thread(grouper).start();
        parse_tree(grouper.token_queue);
    }

    void parse_tree(GrouperToken tokens) throws ParsingException {
        while (tokens.hasNext()) {
            context.add_next_declaration(tokens);
        }
    }

    protected void prepareForParsing() {
        return;
    }

    public abstract RuntimeCodeUnit<? extends CodeUnit> run();

}
