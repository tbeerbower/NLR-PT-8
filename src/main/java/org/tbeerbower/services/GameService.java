package org.tbeerbower.services;

import org.tbeerbower.Game;
import org.tbeerbower.Player;
import org.tbeerbower.view.View;

import java.util.Map;

public class GameService {
    private final Game game;
    private final View view;

    public GameService(Game game, View view) {
        this.game = game;
        this.view = view;
    }

    public void playGame(Player player) {
        int guessCount = 1;
        boolean win = false;

        view.displayDivider();
        while (guessCount <= Game.MAX_GUESSES && !win) {
            view.display(String.format("Please enter guess #%d: ", guessCount));
            String guess = view.getUserString().toUpperCase();
            game.addGuess(guess);
            displayGuesses();
            ++guessCount;
            win = guess.equals(game.getWord());
            if (!win) {
                displayKeyboard();
            }
        }
        if (win) {
            view.displayLine("You won!!");
        } else {
            view.displayLine(String.format("Sorry you didn't win.  The word is %s.", game.getWord()));
        }
        player.addGame(game);
        view.displayLine(String.format("Player %s: %d wins, %d losses, average score %s%n",
                player.getName(), player.getWins(), player.getLosses(), player.getAverageScore()));
    }

    public void displayGuesses() {
        for (String guessToDisplay : game.getGuesses()) {
            int[] results = game.getGuessResults(guessToDisplay);
            displayGuessResults(guessToDisplay, results);
        }
        view.displayLine("");
    }

    public void displayKeyboard() {
        Map<Character, Integer> resultMap = game.getKeyboardResults();
        for (int i = 0; i < View.KEYBOARD.length(); ++i) {
            char keyboardChar = View.KEYBOARD.charAt(i);
            Integer resultCode = resultMap.get(keyboardChar);
            displayResultChar(keyboardChar, resultCode);
        }
        view.displayLine("");
    }

    private void displayGuessResults(String guess, int[] results) {
        for ( int i = 0; i < guess.length(); ++i) {
            char ch = guess.charAt(i);
            int resultCode = results[i];
            displayResultChar(ch, resultCode);
        }
        view.displayLine("");
    }

    private void displayResultChar(char guessChar, Integer resultCode) {
        String color = resultCode == null ? null : game.getGameColors(resultCode) + View.COLOR_BLACK;
        view.display(String.format(" %c ",  guessChar), color);
    }
}
