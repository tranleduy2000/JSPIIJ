package com.js.interpreter.startup;

import java.io.Reader;

public interface ScriptSource {
    /**
     * List possible script contents by name.
     *
     * @return list of source names
     */
    public String[] list();

    /**
     * Open a stream to a given source content.
     *
     * @param scriptname The name of the source
     * @return A reader attached to that source
     */
    public Reader read(String scriptname);
}