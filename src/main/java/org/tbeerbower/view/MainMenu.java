package org.tbeerbower.view;

public class MainMenu extends Menu<MainMenu.Option> {

    public static final Option[] OPTIONS_VALUES = Option.values();

    public enum Option {
        PLAY_WORDLE("Play Wordle game"),
        PLAY_WORDLE_PEAKS("Play Wordle Peaks game"),
        CHANGE_PLAYER("Change player"),
        DISPLAY_PLAYER_STATS("Display player statistics"),
        EXIT("Exit");

        private final String display;

        Option(String display) {
            this.display = display;
        }

        @Override
        public String toString() {
            return display;
        }
    }

    public MainMenu(View view) {
        super(OPTIONS_VALUES, view);
    }
}
