package org.tbeerbower;

import org.tbeerbower.services.GameService;
import org.tbeerbower.services.PlayersService;
import org.tbeerbower.view.Menu;
import org.tbeerbower.view.View;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Terdle {

    // Constants
    private static final String[] MAIN_MENU_OPTIONS =
            {"Play Wordle game", "Play Wordle Peaks game", "Change player", "Display player statistics", "Exit"};
    private static final int PLAY_WORDLE_MENU_OPTION = 1;
    private static final int PLAY_WORDLE_PEAKS_MENU_OPTION = 2;
    private static final int CHANGE_PLAYER_MENU_OPTION = 3;
    private static final int DISPLAY_PLAYER_STATS_MENU_OPTION = 4;
    private static final int EXIT_MENU_OPTION = 5;

    private static final String RESOURCE_DIR = "src/main/resources";
    private static final String PLAYERS_FILE_PATH = "terdle-players.dat";

    // Instance variables
    private final View view;
    private final List<String> words;
    private final List<String> validGuesses;

    public static void main(String[] args) {
        View view = new View(new Scanner(System.in));
        WordReader reader = new WordReader(view);
        List<String> words = reader.getWords(RESOURCE_DIR + "/words.txt");
        List<String> guesses = reader.getWords(RESOURCE_DIR + "/guesses.txt");
        guesses.addAll(words);

        Terdle terdleGame = new Terdle(view, words, guesses);
        terdleGame.run();
    }

    public Terdle(View view, List<String> words, List<String> validGuesses) {
        this.view = view;
        this.words = words;
        this.validGuesses = validGuesses;
    }

    public void run() {
        view.displayLine("Welcome to TErdle!");
        boolean run = true;
        PlayersService playersService = new PlayersService(view, PLAYERS_FILE_PATH);
        playersService.loadPlayers();
        Player currentPlayer = null;

        while(run) {
            if (currentPlayer == null) {
                currentPlayer = playersService.getPlayer();
            }

            Menu mainMenu = new Menu(MAIN_MENU_OPTIONS, view);
            int choice = mainMenu.show();
            switch (choice) {
                case PLAY_WORDLE_MENU_OPTION:
                case PLAY_WORDLE_PEAKS_MENU_OPTION:
                    String word = getRandomWord();
                    Game currentGame = choice == PLAY_WORDLE_MENU_OPTION ?
                            new WordleGame(word, validGuesses) :
                            new WordlePeaksGame(word, validGuesses);
                    GameService gameService = new GameService(currentGame, view);
                    gameService.playGame(currentPlayer);
                    break;
                case CHANGE_PLAYER_MENU_OPTION:
                    currentPlayer = null;
                    break;
                case DISPLAY_PLAYER_STATS_MENU_OPTION:
                    playersService.displayPlayers();
                    break;
                case EXIT_MENU_OPTION:
                    view.displayLine(String.format("Thanks for playing %s!", currentPlayer.getName()));
                    playersService.savePlayers();
                    run = false;
                    break;
            }
        }
    }

    private String getRandomWord() {
        Random random = new Random(System.currentTimeMillis());
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }
}