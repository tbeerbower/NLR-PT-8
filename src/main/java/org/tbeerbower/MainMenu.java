package org.tbeerbower;

import org.tbeerbower.model.Player;
import org.tbeerbower.model.TerdleGame;
import org.tbeerbower.model.WordleGame;
import org.tbeerbower.model.WordlePeaksGame;
import org.tbeerbower.services.GameService;
import org.tbeerbower.services.PlayersService;
import org.tbeerbower.view.Menu;
import org.tbeerbower.view.View;

import java.util.List;
import java.util.Random;

public class MainMenu extends Menu<MainMenu.Option> {

    public static final Option[] OPTIONS_VALUES = Option.values();

    public enum Option {
        PLAY_WORDLE("Play Wordle game"),
        PLAY_WORDLE_PEAKS("Play Wordle Peaks game"),
        CHANGE_PLAYER("Change player"),
        DISPLAY_PLAYER_STATS("Display player statistics"),
        DISPLAY_LEADERBOARD("Display leaderboards"),
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

    public void run() {
        boolean run = true;
        List<String> validGuesses = GameService.getValidGuesses();
        View view = getView();
        PlayersService playersService = new PlayersService(view);
        playersService.loadPlayers();
        Player currentPlayer = null;

        while(run) {
            if (currentPlayer == null) {
                currentPlayer = playersService.getPlayer();
            }

            MainMenu.Option choice = showOptions();
            switch (choice) {
                case PLAY_WORDLE:
                case PLAY_WORDLE_PEAKS:
                    String word = getRandomWord();
                    TerdleGame currentGame = choice == MainMenu.Option.PLAY_WORDLE ?
                            new WordleGame(word, validGuesses) :
                            new WordlePeaksGame(word, validGuesses);
                    GameService gameService = new GameService(currentGame, view);
                    gameService.playGame(currentPlayer);
                    break;
                case CHANGE_PLAYER:
                    currentPlayer = null;
                    break;
                case DISPLAY_PLAYER_STATS:
                    playersService.displayPlayers();
                    break;
                case DISPLAY_LEADERBOARD:
                    LeaderboardMenu leaderboardMenu = new LeaderboardMenu(view);
                    leaderboardMenu.run(playersService);
                    break;
                case EXIT:
                    view.displayLine(String.format("Thanks for playing %s!", currentPlayer.getName()));
                    playersService.savePlayers();
                    run = false;
                    break;
            }
        }
    }

    private String getRandomWord() {
        Random random = new Random(System.currentTimeMillis());
        List<String> words = GameService.getWords();

        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }
}
