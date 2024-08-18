package org.tbeerbower.model;

import org.junit.Before;
import org.junit.Test;
import org.tbeerbower.exception.InvalidGuessException;

import java.util.List;

import static org.junit.Assert.*;

public class BaseUserGameTest extends GameTest {
    
    private static final String INVALID_GUESS = "invalid";  // too long

    private BaseUserGame game;

    @Before
    public void setup() {
        game = getGame();
    }
    
    @Test
    public void getWord() {
        String word = game.getWord();
        assertEquals(UserGame.WORD_LENGTH, word.length());
    }

    @Test
    public void getGuesses() throws Exception {
        List<String> guesses = game.getGuesses();
        assertTrue(guesses.isEmpty());

        String testWord = getTestWord(game);
        game.addGuess(testWord, List.of(TEST_WORDS));
        guesses = game.getGuesses();
        assertEquals(1, guesses.size());
        assertEquals(0, guesses.indexOf(testWord));

        testWord = getTestWord(game);
        game.addGuess(testWord, List.of(TEST_WORDS));
        guesses = game.getGuesses();
        assertEquals(2, guesses.size());
        assertEquals(1, guesses.indexOf(testWord));
    }

    @Test
    public void addGuess() throws Exception {
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < UserGame.MAX_GUESSES; ++i) {
            String testWord = getTestWord(game);
            game.addGuess(testWord, List.of(TEST_WORDS));
            assertEquals(i, guesses.indexOf(testWord));
        }
        assertEquals(UserGame.MAX_GUESSES, guesses.size());
    }

    @Test(expected = InvalidGuessException.class)
    public void addGuess_extra_guess() throws Exception {
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < UserGame.MAX_GUESSES; ++i) {
            game.addGuess(getTestWord(game), List.of(TEST_WORDS));
        }
        assertEquals(UserGame.MAX_GUESSES, guesses.size());
        game.addGuess("extra", List.of(TEST_WORDS));
    }

    @Test(expected = InvalidGuessException.class)
    public void addGuess_invalid_word() throws Exception {
        List<String> guesses = game.getGuesses();
        for (int i = 0; i < UserGame.MAX_GUESSES - 1 ; ++i) {
            game.addGuess(getTestWord(game), List.of(TEST_WORDS));
        }
        assertEquals(UserGame.MAX_GUESSES - 1, guesses.size());
        game.addGuess(INVALID_GUESS, List.of(TEST_WORDS));
    }

    @Test
    public void isWin() throws Exception {
        String word = game.getWord();
        assertFalse(game.isWin());
        for (int i = 0; i < UserGame.MAX_GUESSES - 1; ++i) {
            game.addGuess(getTestWord(game), List.of(TEST_WORDS));
        }
        assertFalse(game.isWin());
        game.addGuess(word, List.of(TEST_WORDS));
        assertTrue(game.isWin());
    }

    @Test
    public void isLoss() throws Exception {
        assertFalse(game.isLoss());
        for (int i = 0; i < UserGame.MAX_GUESSES - 1; ++i) {
            game.addGuess(getTestWord(game), List.of(TEST_WORDS));
        }
        assertFalse(game.isLoss());
        game.addGuess(getTestWord(game), List.of(TEST_WORDS));
        assertTrue(game.isLoss());
    }
}