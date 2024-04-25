package org.tbeerbower;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class BaseGameTest {

    private static final String[] TEST_WORDS = {"funny", "flood", "craze", "store", "shirt", "drive", "acorn", "zebra",
                                                "furry", "black", "frost", "freak", "ghost", "drown", "white", "brown"};
    private static final String INVALID_GUESS = "invalid";  // too long

    private BaseGame game;

    @Before
    public void setup() {
        game = new BaseGame() {
            @Override
            public int[] getGuessResults(String guess) {
                return new int[0];
            }

            @Override
            public Map<Character, Integer> getKeyboardResults() {
                return null;
            }

            @Override
            public String getGameColors(int resultCode) {
                return null;
            }
        };
    }

    @Test
    public void getWord() {
        String word = game.getWord();
        assertEquals(Game.WORD_LENGTH, word.length());
    }

    @Test
    public void getGuesses() {
        String word = game.getWord();
        List<String> guesses = game.getGuesses();
        assertTrue(guesses.isEmpty());

        String testWord = getTestWord(word, guesses);
        game.addGuess(testWord);
        guesses = game.getGuesses();
        assertEquals(1, guesses.size());
        assertEquals(0, guesses.indexOf(testWord));

        testWord = getTestWord(word, guesses);
        game.addGuess(testWord);
        guesses = game.getGuesses();
        assertEquals(2, guesses.size());
        assertEquals(1, guesses.indexOf(testWord));
    }

    @Test
    public void addGuess() {
        String word = game.getWord();
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES; ++i) {
            String testWord = getTestWord(word, guesses);
            game.addGuess(testWord);
            assertEquals(i, guesses.indexOf(testWord));
        }
        assertEquals(Game.MAX_GUESSES, guesses.size());
        game.addGuess("extra");
        assertEquals(Game.MAX_GUESSES, guesses.size());
    }

    @Test
    public void addGuess_extra_guess() {
        String word = game.getWord();
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES; ++i) {
            game.addGuess(getTestWord(word, guesses));
        }
        assertEquals(Game.MAX_GUESSES, guesses.size());
        game.addGuess("extra");
        assertEquals(Game.MAX_GUESSES, guesses.size());
    }

    @Test
    public void addGuess_invalid_word() {
        String word = game.getWord();
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES - 1 ; ++i) {
            game.addGuess(getTestWord(word, guesses));
        }
        assertEquals(Game.MAX_GUESSES - 1, guesses.size());
        game.addGuess(INVALID_GUESS);
        assertEquals(Game.MAX_GUESSES - 1, guesses.size());
        assertFalse(guesses.contains(INVALID_GUESS));
    }

    @Test
    public void isWin() {
        String word = game.getWord();
        assertFalse(game.isWin());
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES - 1; ++i) {
            game.addGuess(getTestWord(word, guesses));
        }
        assertFalse(game.isWin());
        game.addGuess(word);
        assertTrue(game.isWin());
    }

    @Test
    public void isLoss() {
        String word = game.getWord();
        assertFalse(game.isLoss());
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES - 1; ++i) {
            game.addGuess(getTestWord(word, guesses));
        }
        assertFalse(game.isLoss());
        game.addGuess(getTestWord(word, guesses));
        assertTrue(game.isLoss());
    }

    private String getTestWord(String gameWord, List<String> guesses) {
        Random random = new Random(System.currentTimeMillis());
        String testWord;
        do {
            testWord = TEST_WORDS[random.nextInt(TEST_WORDS.length)];
        } while(testWord.equals(gameWord) || guesses.contains(testWord));
        return testWord;
    }
}