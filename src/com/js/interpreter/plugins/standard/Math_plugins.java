package com.js.interpreter.plugins.standard;

import com.js.interpreter.plugins.PascalPlugin;

import java.util.Map;
import java.util.Random;

public class Math_plugins implements PascalPlugin {
    Random r;

    public boolean instantiate(Map<String, Object> pluginargs) {
        r = new Random();
        return true;
    }

    public static int ceil(double d) {
        return (int) Math.ceil(d);
    }

    public static int trunc(double d) {
        return floor(d);
    }

    public static double frac(double d) {
        return d - floor(d);
    }

    public static int floor(double d) {
        return (int) Math.floor(d);
    }

    public static double abs(double d) {
        return Math.abs(d);
    }

    public static int round(double d) {
        return (int) Math.round(d);
    }

    public int random(int range) {
        return r.nextInt(range);
    }

    public static int getsystemtime() {
        return (int) System.currentTimeMillis();
    }

    public static double pow(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public static double sin(double d) {
        return Math.sin(d);
    }

    public static double cos(double d) {
        return Math.cos(d);
    }

    public static double sqr(double d) {
        return d * d;
    }

    public static double sqrt(double d) {
        return Math.sqrt(d);
    }

    public static boolean samevalue(double a, double b) {
        return a == b;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    public static double maxE(double a, double b) {
        return a > b ? a : b;
    }

    public static double minE(double a, double b) {
        return a < b ? a : b;
    }

    public static double intpow(double base, int exp) {
        double result = base;
        while (exp != 0) {
            if ((exp & 1) == 1) {
                exp--;
                result *= base;
            } else {
                exp = exp >> 2;
                result *= result;
            }
        }
        return result;
    }
}
