package org.tbeerbower.model;

import org.tbeerbower.view.View;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class WordlePeaksUserGame extends BaseUserGame {

    // Result Codes
    public enum ResultCode implements UserGame.Result{
        CHAR_LOWER(View.BLUE_BACKGROUND + View.COLOR_BLACK),
        CHAR_HIGHER(View.RED_BACKGROUND + View.COLOR_BLACK),
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

    public WordlePeaksUserGame(Game game, int userId, LocalDate playedDate) {
        super(game, userId, playedDate);
    }

    public WordlePeaksUserGame(int gameId, String word, LocalDate gameDate, int userId, LocalDate playedDate) {
        super(gameId, word, GameType.WORDLE_PEAKS, gameDate, userId, playedDate);
    }

    @Override
    public Result[] getGuessResults(String guess) {
        Result[] resultCodes = new Result[WORD_LENGTH];
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = getWord().charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar) {
                resultCodes[i] = ResultCode.EXACT_MATCH;
            } else if (wordChar > guessChar) {
                resultCodes[i] = ResultCode.CHAR_HIGHER;
            } else {
                resultCodes[i] = ResultCode.CHAR_LOWER;
            }
        }
        return resultCodes;
    }

    @Override
    public Map<Character, Result> getKeyboardResults(){
        Map<Character, Result> resultMap = new HashMap<>();
        for (String currentGuess : getGuesses()) {
            Result[] results = getGuessResults(currentGuess);
            for (int i = 0; i < currentGuess.length(); ++i) {
                if (results[i] == ResultCode.EXACT_MATCH) {
                    resultMap.put(currentGuess.charAt(i), ResultCode.EXACT_MATCH);
                }
            }
        }
        return resultMap;
    }
}
