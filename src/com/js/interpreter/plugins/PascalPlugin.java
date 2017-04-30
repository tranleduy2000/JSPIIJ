package com.js.interpreter.plugins;

import java.util.Map;

public interface PascalPlugin {
    /**
     * This is guaranteed to be called on the plugins instantiation. It is
     * effectively the constructor, because java ServiceLoaders will only call
     * default constructors.
     *
     * @param pluginargs The plugin arguments passed from the script startup.
     * @return true if the plugin has sucessfully been instantiated, and
     * false if it failed. The plugins will only be visible to scripts
     * if this method returns true.
     */
    public boolean instantiate(Map<String, Object> pluginargs);
}
