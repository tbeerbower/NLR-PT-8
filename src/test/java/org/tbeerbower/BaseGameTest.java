package org.tbeerbower;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BaseGameTest extends GameTest {
    
    private static final String INVALID_GUESS = "invalid";  // too long

    private BaseGame game;

    @Before
    public void setup() {
        game = getGame();
    }
    
    @Test
    public void getWord() {
        String word = game.getWord();
        assertEquals(Game.WORD_LENGTH, word.length());
    }

    @Test
    public void getGuesses() throws Exception {
        List<String> guesses = game.getGuesses();
        assertTrue(guesses.isEmpty());

        String testWord = getTestWord(game);
        game.addGuess(testWord);
        guesses = game.getGuesses();
        assertEquals(1, guesses.size());
        assertEquals(0, guesses.indexOf(testWord));

        testWord = getTestWord(game);
        game.addGuess(testWord);
        guesses = game.getGuesses();
        assertEquals(2, guesses.size());
        assertEquals(1, guesses.indexOf(testWord));
    }

    @Test
    public void addGuess() throws Exception {
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES; ++i) {
            String testWord = getTestWord(game);
            game.addGuess(testWord);
            assertEquals(i, guesses.indexOf(testWord));
        }
        assertEquals(Game.MAX_GUESSES, guesses.size());
        game.addGuess("extra");
        assertEquals(Game.MAX_GUESSES, guesses.size());
    }

    @Test
    public void addGuess_extra_guess() throws Exception {
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES; ++i) {
            game.addGuess(getTestWord(game));
        }
        assertEquals(Game.MAX_GUESSES, guesses.size());
        game.addGuess("extra");
        assertEquals(Game.MAX_GUESSES, guesses.size());
    }

    @Test
    public void addGuess_invalid_word() throws Exception {
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < Game.MAX_GUESSES - 1 ; ++i) {
            game.addGuess(getTestWord(game));
        }
        assertEquals(Game.MAX_GUESSES - 1, guesses.size());
        game.addGuess(INVALID_GUESS);
        assertEquals(Game.MAX_GUESSES - 1, guesses.size());
    }

    @Test
    public void isWin() throws Exception {
        String word = game.getWord();
        assertFalse(game.isWin());
        for (int i = 0; i < Game.MAX_GUESSES - 1; ++i) {
            game.addGuess(getTestWord(game));
        }
        assertFalse(game.isWin());
        game.addGuess(word);
        assertTrue(game.isWin());
    }

    @Test
    public void isLoss() throws Exception {
        assertFalse(game.isLoss());
        for (int i = 0; i < Game.MAX_GUESSES - 1; ++i) {
            game.addGuess(getTestWord(game));
        }
        assertFalse(game.isLoss());
        game.addGuess(getTestWord(game));
        assertTrue(game.isLoss());
    }
}