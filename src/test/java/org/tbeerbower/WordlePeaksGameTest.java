package org.tbeerbower;

import org.junit.Before;
import org.junit.Test;
import org.tbeerbower.view.View;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.tbeerbower.WordlePeaksGame.CHAR_HIGHER;
import static org.tbeerbower.WordlePeaksGame.CHAR_LOWER;
import static org.tbeerbower.WordlePeaksGame.EXACT_MATCH;

public class WordlePeaksGameTest {
    private static final String TEST_WORD = "TESTY";
    private WordlePeaksGame game;

    @Before
    public void setup() {
        game = new WordlePeaksGame(TEST_WORD);
    }

    @Test
    public void getGuessResults() {
        int[] results = game.getGuessResults("TASTE");
        assertArrayEquals(new int[] {EXACT_MATCH, CHAR_HIGHER, EXACT_MATCH, EXACT_MATCH, CHAR_HIGHER}, results);

        results = game.getGuessResults("ABOVE");
        assertArrayEquals(new int[] {CHAR_HIGHER, CHAR_HIGHER, CHAR_HIGHER, CHAR_LOWER, CHAR_HIGHER}, results);

        results = game.getGuessResults("ZEBRA");
        assertArrayEquals(new int[] {CHAR_LOWER, EXACT_MATCH, CHAR_HIGHER, CHAR_HIGHER, CHAR_HIGHER}, results);

        results = game.getGuessResults("TITLE");
        assertArrayEquals(new int[] {EXACT_MATCH, CHAR_LOWER, CHAR_LOWER, CHAR_HIGHER, CHAR_HIGHER}, results);

        results = game.getGuessResults(TEST_WORD);
        assertArrayEquals(new int[] {EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH}, results);
    }

    @Test
    public void getKeyboardResults() throws Exception {
        game.addGuess("taste");
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('T', EXACT_MATCH);
        expected.put('S', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
        game.addGuess("tasty");
        expected.put('Y', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
        game.addGuess(TEST_WORD);
        expected.put('E', EXACT_MATCH);
        assertEquals(expected, game.getKeyboardResults());
    }

    @Test
    public void getResultColor() {
        assertEquals(View.BLUE_BACKGROUND, game.getResultColor(CHAR_LOWER));
        assertEquals(View.RED_BACKGROUND, game.getResultColor(CHAR_HIGHER));
        assertEquals(View.GREEN_BACKGROUND, game.getResultColor(EXACT_MATCH));
    }
}