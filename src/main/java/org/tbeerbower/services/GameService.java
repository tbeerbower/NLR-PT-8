package org.tbeerbower.services;

import org.tbeerbower.model.TerdleGame;
import org.tbeerbower.model.InvalidGuessException;
import org.tbeerbower.model.Player;
import org.tbeerbower.model.WordleGame;
import org.tbeerbower.utils.ScannerProvider;
import org.tbeerbower.utils.WordReader;
import org.tbeerbower.view.View;

import java.util.List;
import java.util.Map;

public class GameService {
    // Constants
    private static final String RESOURCE_DIR = "src/main/resources";
    private static List<String> WORDS;
    private static List<String> VALID_GUESSES;

    private final TerdleGame game;
    private final View view;

    public GameService(TerdleGame game, View view) {
        this.game = game;
        this.view = view;
    }

    public static void initWords(View view) {
        WordReader reader = new WordReader(view, new ScannerProvider());
        WORDS = reader.getWords(RESOURCE_DIR + "/words.txt");
        VALID_GUESSES = reader.getWords(RESOURCE_DIR + "/guesses.txt");
        VALID_GUESSES.addAll(WORDS);
    }

    public static List<String> getWords() {
        return WORDS;
    }

    public static List<String> getValidGuesses() {
        return VALID_GUESSES;
    }

    public void playGame(Player player) {
        int guessCount = 1;
        boolean win = false;

        view.displayDivider();
        while (guessCount <= TerdleGame.MAX_GUESSES && !win) {
            promptForGuess(guessCount);
            displayGuesses();
            win = game.isWin();
            if (!win) {
                displayKeyboard();
            }
            ++guessCount;
        }
        if (win) {
            view.displayLine("You won!!");
        } else {
            view.displayLine(String.format("Sorry you didn't win.  The word is %s.", game.getWord()));
        }
        player.addGame(game);
        view.displayLine(player.toString());
    }

    private void promptForGuess(int guessCount) {
        while (true) {
            view.display(String.format("Please enter guess #%d: ", guessCount));
            String guess = view.getUserString();
            try {
                game.addGuess(guess);
                return;
            } catch (InvalidGuessException e) {
                view.displayLine(String.format("'%s' is not a valid guess word! %s", guess, e.getMessage()));
            }
        }
    }

    private void displayGuesses() {
        for (String guessToDisplay : game.getGuesses()) {
            TerdleGame.Result[] results = game.getGuessResults(guessToDisplay);
            displayGuessResults(guessToDisplay, results);
        }
        view.displayLine("");
    }

    private void displayKeyboard() {
        Map<Character, TerdleGame.Result> resultMap = game.getKeyboardResults();
        for (int i = 0; i < View.KEYBOARD.length(); ++i) {
            char keyboardChar = View.KEYBOARD.charAt(i);
            displayResultChar(keyboardChar, resultMap.get(keyboardChar));
        }
        view.displayLine("");
    }

    private void displayGuessResults(String guess, TerdleGame.Result[] results) {
        for (int i = 0; i < guess.length(); ++i) {
            char ch = guess.charAt(i);
            displayResultChar(ch, results[i]);
        }
        view.displayLine("");
    }

    private void displayResultChar(char guessChar, TerdleGame.Result resultCode) {
        String color = resultCode == null ? null : resultCode.getColor();
        view.display(String.format(" %c ", guessChar), color);
    }
}
