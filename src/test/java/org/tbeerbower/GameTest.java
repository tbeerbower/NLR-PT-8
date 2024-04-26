package org.tbeerbower;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class GameTest {
    private static final String[] TEST_WORDS = {"funny", "flood", "craze", "store", "shirt", "drive", "acorn", "zebra",
            "furry", "black", "frost", "freak", "ghost", "drown", "white", "brown"};

    protected String getTestWord(Game game) {
        Random random = new Random(System.currentTimeMillis());
        String gameWord = game.getWord();
        List<String> guesses = game.getGuesses();
        String testWord;
        do {
            testWord = TEST_WORDS[random.nextInt(TEST_WORDS.length)].toUpperCase();
        } while(testWord.equals(gameWord) || guesses.contains(testWord));
        return testWord;
    }

    protected BaseGame getGame() {
        return new BaseGame() {
            @Override
            public int[] getGuessResults(String guess) {
                return new int[0];
            }

            @Override
            public Map<Character, Integer> getKeyboardResults() {
                return null;
            }

            @Override
            public String getResultColor(int resultCode) {
                return null;
            }
        };
    }

    protected Game getWinningGame(int numberOfGuesses) throws InvalidGuessException {
        Game game = getGame();
        for (int i = 0; i < numberOfGuesses - 1; ++i) {
            game.addGuess(getTestWord(game));
        }
        game.addGuess(game.getWord());
        return game;
    }

    protected Game getLosingGame() throws InvalidGuessException {
        Game game = getGame();
        for (int i = 0; i < Game.MAX_GUESSES; ++i) {
            game.addGuess(getTestWord(game));
        }
        return game;
    }
}
