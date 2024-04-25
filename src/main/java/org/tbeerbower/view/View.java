package org.tbeerbower.view;

import java.util.Scanner;

public class View {

    // Constants
    public static final String KEYBOARD =
            "QWERTYUIOP\n" +
                    "ASDFGHJKL\n" +
                    " ZXCVBNM\n";

    // ANSI color codes
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_BLACK = "\u001B[30m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String GRAY_BACKGROUND = "\u001B[47m";

    private final Scanner scanner;

    public View(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUserString() {
        return scanner.nextLine();
    }

    public int getUserInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayLine("Please enter an integer value.");
            }
        }
    }

    public void displayDivider() {
        displayLine("--------------------------");
    }

    public void displayLine(String line) {
        display(line + "\n");
    }

    public void display(String out) {
        System.out.print(out);
    }

    public void display(String out, String color) {
        if (color == null) {
            display(out);
        } else {
            System.out.format("%s%s%s", color, out, COLOR_RESET);
        }
    }
}
