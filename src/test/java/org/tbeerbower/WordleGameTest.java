package org.tbeerbower;

import org.junit.Before;
import org.junit.Test;
import org.tbeerbower.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.tbeerbower.WordleGame.NO_MATCH;
import static org.tbeerbower.WordleGame.WRONG_LOCATION;
import static org.tbeerbower.WordlePeaksGame.EXACT_MATCH;

public class WordleGameTest {

    private WordleGame game;

    @Before
    public void setup() {
        game = new WordleGame(GameTest.TEST_WORD, List.of(GameTest.TEST_WORDS));
    }

    @Test
    public void getGuessResults() {
        int[] results = game.getGuessResults("TASTE");
        assertArrayEquals(new int[] {EXACT_MATCH, NO_MATCH, EXACT_MATCH, EXACT_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults("ABOVE");
        assertArrayEquals(new int[] {NO_MATCH, NO_MATCH, NO_MATCH, NO_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults("ZEBRA");
        assertArrayEquals(new int[] {NO_MATCH, EXACT_MATCH, NO_MATCH, NO_MATCH, NO_MATCH}, results);

        results = game.getGuessResults("TITLE");
        assertArrayEquals(new int[] {EXACT_MATCH, NO_MATCH, WRONG_LOCATION, NO_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults(GameTest.TEST_WORD);
        assertArrayEquals(new int[] {EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH}, results);
    }

    @Test
    public void getKeyboardResults() throws Exception {
        game.addGuess("above");
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('A', NO_MATCH);
        expected.put('B', NO_MATCH);
        expected.put('O', NO_MATCH);
        expected.put('V', NO_MATCH);
        expected.put('E', WRONG_LOCATION);
        assertEquals(expected, game.getKeyboardResults());

        game.addGuess("taste");
        expected.put('T', EXACT_MATCH);
        expected.put('A', NO_MATCH);
        expected.put('S', EXACT_MATCH);
        expected.put('E', WRONG_LOCATION);
        assertEquals(expected, game.getKeyboardResults());

        game.addGuess("title");
        expected.put('I', NO_MATCH);
        expected.put('L', NO_MATCH);
        assertEquals(expected, game.getKeyboardResults());
    }

    @Test
    public void getResultColor() {
        assertEquals(View.GRAY_BACKGROUND, game.getResultColor(NO_MATCH));
        assertEquals(View.YELLOW_BACKGROUND, game.getResultColor(WRONG_LOCATION));
        assertEquals(View.GREEN_BACKGROUND, game.getResultColor(EXACT_MATCH));
    }
}