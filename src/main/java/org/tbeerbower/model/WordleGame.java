package org.tbeerbower.model;

import org.tbeerbower.view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordleGame extends BaseTerdleGame {


    // Result Codes
    public enum ResultCode implements TerdleGame.Result{
        NO_MATCH(View.GRAY_BACKGROUND + View.COLOR_BLACK),
        WRONG_LOCATION(View.YELLOW_BACKGROUND + View.COLOR_BLACK),
        EXACT_MATCH(View.GREEN_BACKGROUND + View.COLOR_BLACK);

        private final String color;

        ResultCode(String color) {
            this.color = color;
        }

        @Override
        public int getValue() {
            return ordinal();
        }

        @Override
        public String getColor() {
            return color;
        }
    }

    public WordleGame(String word, List<String> validWords) {
        super(word, validWords);
    }

    @Override
    public Result[] getGuessResults(String guess ) {
        List<Character> missedWordChars = new LinkedList<>();
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = getWord().charAt(i);
            char guessChar = guess.charAt(i);
            if (wordChar != guessChar) {
                missedWordChars.add(wordChar);
            }
        }

        Result[] resultCodes = new Result[WORD_LENGTH];
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = getWord().charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar) {
                resultCodes[i] = ResultCode.EXACT_MATCH;
            } else {
                int index = missedWordChars.indexOf(guessChar);
                if (index > -1){
                    resultCodes[i] = ResultCode.WRONG_LOCATION;
                    missedWordChars.remove(index);
                } else {
                    resultCodes[i] = ResultCode.NO_MATCH;
                }
            }
        }
        return resultCodes;
    }

    public Map<Character, Result> getKeyboardResults() {
        Map<Character, Result> resultMap = new HashMap<>();
        for (String currentGuess : getGuesses()) {
            Result[] results = getGuessResults(currentGuess);
            for (int i = 0; i < currentGuess.length(); ++i) {
                Character guessChar = currentGuess.charAt(i);
                Result resultCode = resultMap.get(guessChar);
                if (resultCode == null || results[i].getValue() > resultCode.getValue()) {
                    resultMap.put(guessChar, results[i]);
                }
            }
        }
        return resultMap;
    }
}
