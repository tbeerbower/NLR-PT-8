package org.tbeerbower.model;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class GameTest {
    protected static final String TEST_WORD = "TESTY";

    protected static final String[] TEST_WORDS = {
            "above", "title", "funny", "flood", "craze", "store", "shirt", "drive", "acorn", "zebra",
            "furry", "black", "frost", "freak", "ghost", "drown", "white", "brown", "testy", "tasty", "taste", "extra"};

    protected String getTestWord(TerdleGame game) {
        Random random = new Random(System.currentTimeMillis());
        String gameWord = game.getWord();
        List<String> guesses = game.getGuesses();
        String testWord;
        do {
            testWord = TEST_WORDS[random.nextInt(TEST_WORDS.length)].toUpperCase();
        } while(testWord.equals(gameWord) || guesses.contains(testWord));
        return testWord;
    }

    protected BaseTerdleGame getGame() {
        return new BaseTerdleGame(TEST_WORD, List.of(TEST_WORDS)) {
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

    protected TerdleGame getWinningGame(int numberOfGuesses) throws InvalidGuessException {
        TerdleGame game = getGame();
        for (int i = 0; i < numberOfGuesses - 1; ++i) {
            game.addGuess(getTestWord(game));
        }
        game.addGuess(game.getWord());
        return game;
    }

    protected TerdleGame getLosingGame() throws InvalidGuessException {
        TerdleGame game = getGame();
        for (int i = 0; i < TerdleGame.MAX_GUESSES; ++i) {
            game.addGuess(getTestWord(game));
        }
        return game;
    }
}
