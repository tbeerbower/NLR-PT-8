package org.tbeerbower;

import org.tbeerbower.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordlePeaksGame extends BaseGame {

    // Result Codes
    protected static final int CHAR_LOWER = 0;
    protected static final int CHAR_HIGHER = 1;
    protected static final int EXACT_MATCH = 2;

    private static final String[] BACKGROUNDS = {View.BLUE_BACKGROUND, View.RED_BACKGROUND, View.GREEN_BACKGROUND};

    public WordlePeaksGame(String word, List<String> validWords) {
        super(word, validWords);
    }

    @Override
    public int[] getGuessResults(String guess) {

        int[] resultCodes = new int[WORD_LENGTH];
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = getWord().charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar) {
                resultCodes[i] = EXACT_MATCH;
            } else if (wordChar > guessChar) {
                resultCodes[i] = CHAR_HIGHER;
            } else {
                resultCodes[i] = CHAR_LOWER;
            }
        }
        return resultCodes;
    }

    @Override
    public Map<Character, Integer> getKeyboardResults(){
        Map<Character, Integer> resultMap = new HashMap<>();
        for (String currentGuess : getGuesses()) {
            int[] results = getGuessResults(currentGuess);
            for (int i = 0; i < currentGuess.length(); ++i) {
                if (results[i] == EXACT_MATCH) {
                    resultMap.put(currentGuess.charAt(i), EXACT_MATCH);
                }
            }
        }
        return resultMap;
    }

    @Override
    public String getResultColor(int resultCode) {
        return BACKGROUNDS[resultCode];
    }
}
