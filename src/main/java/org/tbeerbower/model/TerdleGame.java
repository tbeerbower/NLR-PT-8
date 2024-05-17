package org.tbeerbower.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TerdleGame extends Serializable {

    // Constants
    int WORD_LENGTH = 5;
    int MAX_GUESSES = 6;
    int TOTAL_GUESSES_FOR_LOSS = 7;

    String getWord();
    List<String> getGuesses();
    void addGuess(String guess) throws InvalidGuessException;
    Result[] getGuessResults(String guess);
    Map<Character, Result> getKeyboardResults();
    boolean isWin();
    boolean isLoss();

    /**
     * Result interface represents the result of matching a character from a guess to the game word.
     */
    interface Result {
        int getValue();
        String getColor();
    }
}
