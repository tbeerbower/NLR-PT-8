package org.tbeerbower;

import org.tbeerbower.view.View;

import java.util.List;
import java.util.Map;

public interface Game {

    // Constants
    int WORD_LENGTH = 5;
    int MAX_GUESSES = 6;
    int TOTAL_GUESSES_FOR_LOSS = 7;

    String getWord();
    List<String> getGuesses();
    void addGuess(String guess);
    int[] getGuessResults(String guess );
    Map<Character, Integer> getKeyboardResults();
    boolean isWin();
    boolean isLoss();
    String getGameColors(int resultCode);
}