package org.tbeerbower.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.tbeerbower.model.WordlePeaksUserGame.ResultCode.CHAR_HIGHER;
import static org.tbeerbower.model.WordlePeaksUserGame.ResultCode.CHAR_LOWER;
import static org.tbeerbower.model.WordlePeaksUserGame.ResultCode.EXACT_MATCH;

public class WordlePeaksUserGameTest {
    private WordlePeaksUserGame game;

    @Before
    public void setup() {
        LocalDate now = LocalDate.now();
        game = new WordlePeaksUserGame(0, GameTest.TEST_WORD, now, 1, now);
    }

    @Test
    public void getGuessResults() {
        UserGame.Result[] results = game.getGuessResults("TASTE");
        assertArrayEquals(new UserGame.Result[] {EXACT_MATCH, CHAR_HIGHER, EXACT_MATCH, EXACT_MATCH, CHAR_HIGHER}, results);

        results = game.getGuessResults("ABOVE");
        assertArrayEquals(new UserGame.Result[] {CHAR_HIGHER, CHAR_HIGHER, CHAR_HIGHER, CHAR_LOWER, CHAR_HIGHER}, results);

        results = game.getGuessResults("ZEBRA");
        assertArrayEquals(new UserGame.Result[] {CHAR_LOWER, EXACT_MATCH, CHAR_HIGHER, CHAR_HIGHER, CHAR_HIGHER}, results);

        results = game.getGuessResults("TITLE");
        assertArrayEquals(new UserGame.Result[] {EXACT_MATCH, CHAR_LOWER, CHAR_LOWER, CHAR_HIGHER, CHAR_HIGHER}, results);

        results = game.getGuessResults(GameTest.TEST_WORD);
        assertArrayEquals(new UserGame.Result[] {EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH}, results);
    }

    @Test
    public void getKeyboardResults() throws Exception {
        game.addGuess("taste", List.of(GameTest.TEST_WORDS));
        Map<Character, UserGame.Result> expected = new HashMap<>();
        expected.put('T', EXACT_MATCH);
        expected.put('S', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
        game.addGuess("tasty", List.of(GameTest.TEST_WORDS));
        expected.put('Y', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
        game.addGuess(GameTest.TEST_WORD, List.of(GameTest.TEST_WORDS));
        expected.put('E', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
    }
}