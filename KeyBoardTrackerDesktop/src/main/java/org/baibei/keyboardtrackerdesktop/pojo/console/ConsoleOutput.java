package org.baibei.keyboardtrackerdesktop.pojo.console;

import java.util.List;

public class ConsoleOutput {

    // Colors
    public static final String COLOR_RESET = "\033[0m";      // Text Reset
    public static final String COLOR_BLACK = "\033[0;30m";   // BLACK
    public static final String COLOR_RED = "\033[0;31m";     // RED
    public static final String COLOR_GREEN = "\033[0;32m";   // GREEN
    public static final String COLOR_YELLOW = "\033[0;33m";  // YELLOW
    public static final String COLOR_BLUE = "\033[0;34m";    // BLUE
    public static final String COLOR_PURPLE = "\033[0;35m";  // PURPLE
    public static final String COLOR_CYAN = "\033[0;36m";    // CYAN
    public static final String COLOR_WHITE = "\033[0;37m";   // WHITE

    private static final int standardLineLength = 62;

    public static String getColorCode(Color color) {
        return switch (color) {
            case BLACK -> COLOR_BLACK;
            case RED -> COLOR_RED;
            case GREEN -> COLOR_GREEN;
            case YELLOW -> COLOR_YELLOW;
            case BLUE -> COLOR_BLUE;
            case PURPLE -> COLOR_PURPLE;
            case CYAN -> COLOR_CYAN;
            case WHITE -> COLOR_WHITE;
        };
    }

    public static void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void printLine() {
        printLine(standardLineLength);
    }

    public static void printHeader(String header) {
        System.out.println(header.toUpperCase());
    }

    public static void printHeader(String header, Color color) {
        System.out.println(getColorCode(color) + header.toUpperCase() + COLOR_RESET);
    }

    public static void printBody(String body) {
        System.out.println(body);
    }

    public static void printBody(List<String> body) {
        for (String s : body) {
            System.out.println(s);
        }
    }

    public static void printBody(String[] body) {
        for (String s : body) {
            System.out.println(s);
        }
    }

    public static void printFooter(String footer) {
        System.out.println(footer.toLowerCase());
    }

    public static void print(String header, String message, String footer, int length, Color color) {
        System.out.println(getColorCode(color));
        printHeader(header);
        printLine(length);
        printBody(message);
        printLine(length);
        printFooter(footer);
        printLine(length);
        System.out.println(COLOR_RESET);
    }

    public static void print(String header, String message, String footer, int length) {
        System.out.println();
        printHeader(header);
        printLine(length);
        printBody(message);
        printLine(length);
        printFooter(footer);
        printLine(length);
        System.out.println();
    }

    public static void print(String header, String message, String footer) {
        print(header, message, footer, standardLineLength);
    }

    public static void print(String header, String message, String footer, Color color) {
        print(header, message, footer, standardLineLength, color);
    }

    public static void print(String header, String message, int lineLength, Color color) {
        System.out.println(getColorCode(color));
        printHeader(header);
        printLine(lineLength);
        printBody(message);
        printLine(lineLength);
        System.out.println(COLOR_RESET);
    }

    public static void print(String header, String message, int lineLength) {
        System.out.println();
        printHeader(header);
        printLine(lineLength);
        printBody(message);
        printLine(lineLength);
        System.out.println();
    }

    public static void print(String header, String message) {
        print(header, message, standardLineLength);
    }

    public static void print(String header, String message, Color color) {
        print(header, message, standardLineLength, color);
    }

    public static void error(String message, int lineLength) {
        print("error", message, lineLength, Color.RED);
    }

    public static void error(String message) {
        error(message, standardLineLength);
    }

    public static void info(String message, int lineLength) {
        print("info", message, lineLength, Color.BLUE);
    }

    public static void info(String message) {
        info(message, standardLineLength);
    }

    public static void success(String message, int lineLength) {
        print("success", message, lineLength, Color.GREEN);
    }

    public static void success(String message) {
        success(message, standardLineLength);
    }

    public static void success(int lineLength) {
        printHeader("success", Color.GREEN);
        printLine(lineLength);
        System.out.println();
    }

    public static void success() {
        success(standardLineLength);
    }

    public static void warning(String message, int lineLength) {
        print("warning", message, lineLength, Color.YELLOW);
    }

    public static void warning(String message) {
        warning(message, standardLineLength);
    }

    public static void fatal(String message, int lineLength) {
        print("fatal error", message, lineLength, Color.RED);
    }

    public static void fatal(String message) {
        fatal(message, standardLineLength);
    }

    public static String getLine(int lineLength) {
        StringBuilder sb = new StringBuilder();
        sb.append("-".repeat(Math.max(0, lineLength)));
        return sb.toString();
    }

    public static String getLine() {
        return getLine(standardLineLength);
    }
}
