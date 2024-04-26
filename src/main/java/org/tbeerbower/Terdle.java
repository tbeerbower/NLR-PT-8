package org.tbeerbower;

import org.tbeerbower.services.GameService;
import org.tbeerbower.services.PlayersService;
import org.tbeerbower.view.Menu;
import org.tbeerbower.view.View;

import java.util.Scanner;

public class Terdle {

    // Constants
    private static final String[] MAIN_MENU_OPTIONS =
            {"Play Wordle game", "Play Wordle Peaks game", "Change player", "Exit"};
    private static final int PLAY_WORDLE_MENU_OPTION = 1;
    private static final int PLAY_WORDLE_PEAKS_MENU_OPTION = 2;
    private static final int CHANGE_PLAYER_MENU_OPTION = 3;
    private static final int EXIT_MENU_OPTION = 4;

    // Instance variables
    private final View view;

    public static void main(String[] args) {
        Terdle terdleGame = new Terdle(new View(new Scanner(System.in)));
        terdleGame.run();
    }

    public Terdle(View view) {
        this.view = view;
    }

    public void run() {
        view.displayLine("Welcome to TErdle!");
        boolean run = true;
        PlayersService playersService = new PlayersService(view);
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
                    Game currentGame = choice == PLAY_WORDLE_MENU_OPTION ?
                            new WordleGame() : new WordlePeaksGame();
                    GameService gameService = new GameService(currentGame, view);
                    gameService.playGame(currentPlayer);
                    break;
                case CHANGE_PLAYER_MENU_OPTION:
                    currentPlayer = null;
                    break;
                case EXIT_MENU_OPTION:
                    view.displayLine(String.format("Thanks for playing %s!", currentPlayer.getName()));
                    run = false;
                    break;
            }
        }
    }
}