package com.js.interpreter.plugins.standard;

import com.js.interpreter.plugins.PascalPlugin;
import com.js.interpreter.runtime.PascalReference;
import com.js.interpreter.runtime.exception.RuntimePascalException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class String_plugins implements PascalPlugin {
    public static String between(String s1, String s2, String s) {
        int startindex = s.indexOf(s1) + s1.length();
        int endindex = s.indexOf(s2, startindex);
        return s.substring(startindex, endindex);
    }

    public static String capitalize(String s) {
        boolean lastSpace = true;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (lastSpace) {
                chars[i] = Character.toUpperCase(chars[i]);
                lastSpace = false;
            }
            if (chars[i] == ' ') {
                lastSpace = true;
            }
        }
        return new String(chars);
    }

    public static String copy(String s, int ifrom, int count) {
        return s.substring(ifrom, ifrom + count);
    }

    public static void delete(PascalReference<StringBuilder> s, int ifrom,
                              int count) throws RuntimePascalException {
        s.set(s.get().delete(ifrom - 1, ifrom + count - 1));
    }

    public static boolean endswith(String suffix, String tosearch) {
        return tosearch.endsWith(suffix);
    }

    public static String findregex(String tosearch, String regex) {
        Pattern reg = Pattern.compile(regex);
        Matcher m = reg.matcher(tosearch);
        if (!m.find()) {
            return "";
        }
        return tosearch.substring(m.start(), m.end());
    }

    public static String getletters(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String getnumbers(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String getothers(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static boolean InStrArr(String Str, String[] Arr,
                                   boolean casesensitive) {
        for (String s : Arr) {
            if (casesensitive ? s.equals(Str) : s.equalsIgnoreCase(Str)) {
                return true;
            }
        }
        return false;
    }

    public static void insert(String toinsert,
                              PascalReference<StringBuilder> reciever, int pos)
            throws RuntimePascalException {
        reciever.set(reciever.get().insert(pos - 1, toinsert));
    }

    public static int LastPosEx(String tofind, String findin, int from) {
        return findin.lastIndexOf(tofind, from);
    }

    public static int LastPos(String tofind, String findin) {
        return findin.lastIndexOf(tofind);
    }

    public static String Left(String s, int count) {
        return s.substring(0, count);
    }

    public static int length(String s) {
        return s.length();
    }

    public static String md5(String s) throws NoSuchAlgorithmException {
        MessageDigest digester = MessageDigest.getInstance("MD5");
        digester.update(s.getBytes());
        return new BigInteger(1, digester.digest()).toString(16);
    }

    public static String padl(String topad, int size) {
        StringBuilder result = new StringBuilder(size);
        for (int i = topad.length(); i < size; i++) {
            result.append(' ');
        }
        result.append(topad);
        return result.toString();
    }

    public static String padr(String topad, int size) {
        StringBuilder result = new StringBuilder(size);
        result.append(topad);
        for (int i = topad.length(); i < size; i++) {
            result.append(' ');
        }
        return result.toString();
    }

    public static String padz(String topad, int size) {
        StringBuilder result = new StringBuilder(size);
        for (int i = topad.length(); i < size; i++) {
            result.append('0');
        }
        result.append(topad);
        return result.toString();
    }

    public static int posex(String tofind, String s, int startindex) {
        return s.indexOf("\\Q" + tofind, startindex);
    }

    public static int pos(String substring, String s) {
        return s.indexOf(substring) + 1;
    }

    public static int regexpos(String text, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        m.find();
        int i = m.start();
        if (i >= 0) {
            i++;
        }
        return i;
    }

    public static String replaceregex(String text, String regex,
                                      String replacetext) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        return m.replaceAll(replacetext);
    }

    public static String replace(String text, String tofind, String replacement) {
        return text.replaceAll("\\Q" + tofind, replacement);
    }

    public static String replicate(char c, int times) {
        StringBuilder result = new StringBuilder(times);
        for (int i = 0; i < times; i++) {
            result.append(c);
        }
        return result.toString();
    }

    public static String right(String s, int length) {
        return s.substring(s.length() - length, s.length());
    }

    public static void setlength(PascalReference<StringBuilder> s, int length)
            throws RuntimePascalException {
        String filler = "!@#$%";
        StringBuilder old = s.get();
        if (length <= old.length()) {
            s.set(new StringBuilder(old.subSequence(0, length)));
        } else {
            int extra = length - old.length();
            int count = extra / filler.length();
            StringBuilder result = new StringBuilder(length);
            result.append(old);
            for (int i = 0; i < count; i++) {
                result.append(filler);
            }
            int remaining = extra - count * filler.length();
            result.append(filler.subSequence(0, remaining));
            s.set(result);
        }
    }

    public static boolean startswith(String prefix, String s) {
        return s.startsWith(prefix);
    }

    public static char strget(PascalReference<StringBuilder> s, int index)
            throws RuntimePascalException {
        return s.get().charAt(index);
    }

    public static void strset(char c, int index, PascalReference<StringBuilder> s)
            throws RuntimePascalException {

        s.get().setCharAt(index, c);
        s.set(s.get());
    }

    public static String stringofchar(char c, int times) {
        return replicate(c, times);
    }

    public static String trimex(String delimeter, String s) {
        int beginningindex = 0;
        while (s.startsWith(delimeter, beginningindex)) {
            beginningindex += delimeter.length();
        }
        int endindex = s.length();
        while (s.lastIndexOf(delimeter, endindex) == endindex
                - delimeter.length()) {
            endindex -= delimeter.length();
        }
        return s.substring(beginningindex, endindex);
    }

    public static String trim(String s) {
        return s.trim();
    }

    public static String trimletters(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String trimnumbers(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String trimothers(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c) || Character.isLetter(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String uppercase(String s) {
        return s.toUpperCase();
    }

    /**
     * @param c - input
     * @return the upper case of c
     */
    public static char upCase(char c) {
        return Character.toUpperCase(c);
    }

    @Override
    public boolean instantiate(Map<String, Object> pluginargs) {
        return true;
    }
}
