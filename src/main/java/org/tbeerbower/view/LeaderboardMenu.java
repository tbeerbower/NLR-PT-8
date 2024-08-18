package org.tbeerbower.view;

import org.tbeerbower.services.PlayerService;

import static org.tbeerbower.model.UserComparator.Mode.AVG_GUESSES_ASC;
import static org.tbeerbower.model.UserComparator.Mode.WINS_DESC;

/**
 * Sub menu class for the leaderboard options of the TErdle game.
 */
public class LeaderboardMenu extends Menu<LeaderboardMenu.Option> {

    public static final Option[] OPTIONS_VALUES = Option.values();

    // Enum for leaderboard menu options
    public enum Option {
        BY_GAMES_WON("Leaderboard by games won"),
        BY_AVG_GUESSES("Leaderboard by average guesses"),
        EXIT("Return to main menu");

        private final String display;

        Option(String display) {
            this.display = display;
        }

        @Override
        public String toString() {
            return display;
        }
    }


    // ***** Constructors *****************************************************

    public LeaderboardMenu(View view) {
        super(OPTIONS_VALUES, view);
    }


    // ***** LeaderboardMenu **************************************************

    /**
     * Run the menu and show the options, waiting for a user selection.
     * @param playerService  the service used to display the player leaderboards
     */
    public void run(PlayerService playerService) {
        boolean run = true;

        while (run) {
            switch (showOptions()) {
                case BY_GAMES_WON:
                    getView().displayDivider();
                    getView().displayLine("Leaderboard by games won");
                    playerService.displayPlayers(getView(), WINS_DESC);
                    break;
                case BY_AVG_GUESSES:
                    getView().displayDivider();
                    getView().displayLine("Leaderboard by average guesses");
                    playerService.displayPlayers(getView(), AVG_GUESSES_ASC);
                    break;
                case EXIT:
                    run = false;
                    break;
            }
        }
    }
}
