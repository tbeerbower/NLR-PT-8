package org.tbeerbower.view;

import org.tbeerbower.model.GameType;
import org.tbeerbower.model.User;
import org.tbeerbower.services.GameService;
import org.tbeerbower.services.PlayerService;

public class MainMenu extends Menu<MainMenu.Option> {

    public static final Option[] OPTIONS_VALUES = Option.values();

    public enum Option {
        PLAY_WORDLE("Play Wordle game"),
        PLAY_WORDLE_PEAKS("Play Wordle Peaks game"),
        DISPLAY_PLAYER_STATS("Display player statistics"),
        DISPLAY_LEADERBOARD("Display leaderboards"),
        CHANGE_PLAYER("Log out"),
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

    private final PlayerService playerService;
    private final GameService gameService;

    public MainMenu(View view, PlayerService playerService, GameService gameService) {
        super(OPTIONS_VALUES, view);
        this.playerService = playerService;
        this.gameService = gameService;
    }

    public void run() {
        boolean run = true;
        View view = getView();
        User currentUser = null;

        while(run) {
            while (currentUser == null) {
                currentUser = playerService.getPlayer(view);
            }

            MainMenu.Option choice = showOptions();
            switch (choice) {
                case PLAY_WORDLE:
                    gameService.playGame(view, currentUser, GameType.WORDLE);
                    break;
                case PLAY_WORDLE_PEAKS:
                    gameService.playGame(view, currentUser, GameType.WORDLE_PEAKS);
                    break;
                case CHANGE_PLAYER:
                    currentUser = null;
                    break;
                case DISPLAY_PLAYER_STATS:
                    playerService.displayPlayers(view);
                    break;
                case DISPLAY_LEADERBOARD:
                    LeaderboardMenu leaderboardMenu = new LeaderboardMenu(view);
                    leaderboardMenu.run(playerService);
                    break;
                case EXIT:
                    view.displayLine(String.format("Thanks for playing %s!", currentUser.getUsername()));
                    run = false;
                    break;
            }
        }
    }
}
