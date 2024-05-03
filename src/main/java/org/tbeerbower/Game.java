package org.tbeerbower;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Game extends Serializable {

    // Constants
    int WORD_LENGTH = 5;
    int MAX_GUESSES = 6;
    int TOTAL_GUESSES_FOR_LOSS = 7;

    String getWord();
    List<String> getGuesses();
    void addGuess(String guess) throws InvalidGuessException;
    int[] getGuessResults(String guess );
    Map<Character, Integer> getKeyboardResults();
    boolean isWin();
    boolean isLoss();
    String getResultColor(int resultCode);
}
