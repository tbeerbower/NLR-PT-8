package org.tbeerbower;

import org.tbeerbower.services.PlayersService;
import org.tbeerbower.view.Menu;
import org.tbeerbower.view.View;

import static org.tbeerbower.model.PlayerComparator.Mode.AVG_GUESSES_ASC;
import static org.tbeerbower.model.PlayerComparator.Mode.WINS_DESC;

public class LeaderboardMenu extends Menu<LeaderboardMenu.Option> {

    public static final Option[] OPTIONS_VALUES = Option.values();

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

    public LeaderboardMenu(View view) {
        super(OPTIONS_VALUES, view);
    }

    public void run(PlayersService playersService) {
        boolean run = true;

        while (run) {
            switch (showOptions()) {
                case BY_GAMES_WON:
                    getView().displayDivider();
                    getView().displayLine("Leaderboard by games won");
                    playersService.displayPlayers(WINS_DESC);
                    break;
                case BY_AVG_GUESSES:
                    getView().displayDivider();
                    getView().displayLine("Leaderboard by average guesses");
                    playersService.displayPlayers(AVG_GUESSES_ASC);
                    break;
                case EXIT:
                    run = false;
                    break;
            }
        }
    }
}
