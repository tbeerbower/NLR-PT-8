package org.tbeerbower.model;

import org.tbeerbower.exception.InvalidGuessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class GameTest {
    public static final String TEST_WORD = "TESTY";

    public static final String[] TEST_WORDS = {
            "above", "title", "funny", "flood", "craze", "store", "shirt", "drive", "acorn", "zebra",
            "furry", "black", "frost", "freak", "ghost", "drown", "white", "brown", "testy", "tasty", "taste", "extra"};

    protected String getTestWord(UserGame game) {
        Random random = new Random(System.currentTimeMillis());
        String gameWord = game.getWord();
        List<String> guesses = game.getGuesses();
        String testWord;
        do {
            testWord = TEST_WORDS[random.nextInt(TEST_WORDS.length)].toUpperCase();
        } while(testWord.equals(gameWord) || guesses.contains(testWord));
        return testWord;
    }

    protected BaseUserGame getGame() {
        LocalDate now = LocalDate.now();
        return new BaseUserGame(0, TEST_WORD, GameType.WORDLE, now, 1, now) {
            @Override
            public Result[] getGuessResults(String guess) {
                return new Result[0];
            }

            @Override
            public Map<Character, Result> getKeyboardResults() {
                return null;
            }
        };
    }

    protected UserGame getWinningGame(int numberOfGuesses) throws InvalidGuessException {
        UserGame game = getGame();
        for (int i = 0; i < numberOfGuesses - 1; ++i) {
            game.addGuess(getTestWord(game), List.of(TEST_WORDS));
        }
        game.addGuess(game.getWord(), List.of(TEST_WORDS));
        return game;
    }

    protected UserGame getLosingGame() throws InvalidGuessException {
        UserGame game = getGame();
        for (int i = 0; i < UserGame.MAX_GUESSES; ++i) {
            game.addGuess(getTestWord(game), List.of(TEST_WORDS));
        }
        return game;
    }
}
