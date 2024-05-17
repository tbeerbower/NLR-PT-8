package org.tbeerbower.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.tbeerbower.model.WordlePeaksGame.ResultCode.CHAR_HIGHER;
import static org.tbeerbower.model.WordlePeaksGame.ResultCode.CHAR_LOWER;
import static org.tbeerbower.model.WordlePeaksGame.ResultCode.EXACT_MATCH;

public class WordlePeaksGameTest {
    private WordlePeaksGame game;

    @Before
    public void setup() {
        game = new WordlePeaksGame(GameTest.TEST_WORD, List.of(GameTest.TEST_WORDS));
    }

    @Test
    public void getGuessResults() {
        TerdleGame.Result[] results = game.getGuessResults("TASTE");
        assertArrayEquals(new TerdleGame.Result[] {EXACT_MATCH, CHAR_HIGHER, EXACT_MATCH, EXACT_MATCH, CHAR_HIGHER}, results);

        results = game.getGuessResults("ABOVE");
        assertArrayEquals(new TerdleGame.Result[] {CHAR_HIGHER, CHAR_HIGHER, CHAR_HIGHER, CHAR_LOWER, CHAR_HIGHER}, results);

        results = game.getGuessResults("ZEBRA");
        assertArrayEquals(new TerdleGame.Result[] {CHAR_LOWER, EXACT_MATCH, CHAR_HIGHER, CHAR_HIGHER, CHAR_HIGHER}, results);

        results = game.getGuessResults("TITLE");
        assertArrayEquals(new TerdleGame.Result[] {EXACT_MATCH, CHAR_LOWER, CHAR_LOWER, CHAR_HIGHER, CHAR_HIGHER}, results);

        results = game.getGuessResults(GameTest.TEST_WORD);
        assertArrayEquals(new TerdleGame.Result[] {EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH}, results);
    }

    @Test
    public void getKeyboardResults() throws Exception {
        game.addGuess("taste");
        Map<Character, TerdleGame.Result> expected = new HashMap<>();
        expected.put('T', EXACT_MATCH);
        expected.put('S', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
        game.addGuess("tasty");
        expected.put('Y', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
        game.addGuess(GameTest.TEST_WORD);
        expected.put('E', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
    }
}