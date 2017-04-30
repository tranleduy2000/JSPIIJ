package com.js.interpreter.startup;

import java.io.*;

public class FileScriptSource implements ScriptSource {
    public FileScriptSource(File directory) {
        this.directory = directory;
    }

    public FileScriptSource(String directory) {
        this.directory = new File(directory);
    }

    File directory;

    @Override
    public String[] list() {
        File[] children = directory.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        if (children == null) {
            return new String[0];
        }
        String[] result = new String[children.length];
        for (int i = 0; i < children.length; i++) {
            result[i] = children[i].getName();
        }
        return result;
    }

    @Override
    public Reader read(String scriptname) {
        try {
            return new FileReader(new File(directory, scriptname));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

}
