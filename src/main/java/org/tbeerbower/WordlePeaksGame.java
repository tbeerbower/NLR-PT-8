package org.tbeerbower;

import java.util.HashMap;
import java.util.Map;

public class WordlePeaksGame extends BaseGame {

    // Result Codes
    private static final int CHAR_LOWER = 0;
    private static final int CHAR_HIGHER = 1;
    private static final int EXACT_MATCH = 2;

    private static final String[] BACKGROUNDS = {View.RED_BACKGROUND, View.BLUE_BACKGROUND, View.GREEN_BACKGROUND};

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
                Character guessChar = currentGuess.charAt(i);
                Integer resultCode = resultMap.get(guessChar);
                resultMap.put(guessChar, results[i]);
            }
        }
        return resultMap;
    }

    @Override
    public String getGameColors(int resultCode) {
        return BACKGROUNDS[resultCode];
    }
}
