package org.tbeerbower;

import org.tbeerbower.view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordleGame extends BaseGame {


    // Result Codes
    protected static final int NO_MATCH = 0;
    protected static final int WRONG_LOCATION = 1;
    protected static final int EXACT_MATCH = 2;

    // backgrounds
    private static final String[] BACKGROUNDS = {View.GRAY_BACKGROUND, View.YELLOW_BACKGROUND, View.GREEN_BACKGROUND};

    public WordleGame(String word, List<String> validWords) {
        super(word, validWords);
    }

    @Override
    public int[] getGuessResults(String guess ) {
        List<Character> missedWordChars = new LinkedList<>();
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = getWord().charAt(i);
            char guessChar = guess.charAt(i);
            if (wordChar != guessChar) {
                missedWordChars.add(wordChar);
            }
        }

        int[] resultCodes = new int[WORD_LENGTH];
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = getWord().charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar) {
                resultCodes[i] = EXACT_MATCH;
            } else {
                int index = missedWordChars.indexOf(guessChar);
                if (index > -1){
                    resultCodes[i] = WRONG_LOCATION;
                    missedWordChars.remove(index);
                } else {
                    resultCodes[i] = NO_MATCH;
                }
            }
        }
        return resultCodes;
    }

    public Map<Character, Integer> getKeyboardResults() {
        Map<Character, Integer> resultMap = new HashMap<>();
        for (String currentGuess : getGuesses()) {
            int[] results = getGuessResults(currentGuess);
            for (int i = 0; i < currentGuess.length(); ++i) {
                Character guessChar = currentGuess.charAt(i);
                Integer resultCode = resultMap.get(guessChar);
                if (resultCode == null || results[i] > resultCode) {
                    resultMap.put(guessChar, results[i]);
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
