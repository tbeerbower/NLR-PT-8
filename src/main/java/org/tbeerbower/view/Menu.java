package org.tbeerbower.view;

public class Menu<T> {
    private final T[] options;
    private final View view;

    public Menu(T[] options, View view) {
        this.options = options;
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public int show() {
        view.displayDivider();
        for (int i = 0; i < options.length; ++i) {
            view.displayLine((i + 1) + ") " + options[i]);
        }
        while (true) {
            view.displayLine(String.format("Please enter your choice (1-%d)", options.length));
            int choice = view.getUserInt();
            if (choice >= 1 && choice <= options.length) {
                return choice;
            }
            view.displayLine(String.format("%d is not a valid choice!", choice));
        }
    }

    public T showOptions() {
        return options[show() - 1];
    }
}
