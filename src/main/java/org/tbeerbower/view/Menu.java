package org.tbeerbower.view;

public class Menu {
    private final String[] options;
    private final View view;

    public Menu(String[] options, View view) {
        this.options = options;
        this.view = view;
    }

    public int show() {
        view.displayDivider();
        for (int i = 0; i < options.length; ++i) {
            view.displayLine((i + 1) + ") " + options[i]);
        }
        while (true) {
            view.displayLine("Please enter your choice (1-" + options.length + ")");
            int choice = view.getUserInt();
            if (choice >= 1 && choice <= options.length) {
                return choice;
            }
            view.displayLine(choice + " is not a valid choice!");
        }
    }
}
