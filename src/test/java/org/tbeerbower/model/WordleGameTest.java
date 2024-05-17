package org.tbeerbower.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.tbeerbower.model.WordleGame.ResultCode.EXACT_MATCH;
import static org.tbeerbower.model.WordleGame.ResultCode.NO_MATCH;
import static org.tbeerbower.model.WordleGame.ResultCode.WRONG_LOCATION;

public class WordleGameTest {

    private WordleGame game;

    @Before
    public void setup() {
        game = new WordleGame(GameTest.TEST_WORD, List.of(GameTest.TEST_WORDS));
    }

    @Test
    public void getGuessResults() {
        TerdleGame.Result[] results = game.getGuessResults("TASTE");
        assertArrayEquals(new TerdleGame.Result[] {EXACT_MATCH, NO_MATCH, EXACT_MATCH, EXACT_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults("ABOVE");
        assertArrayEquals(new TerdleGame.Result[] {NO_MATCH, NO_MATCH, NO_MATCH, NO_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults("ZEBRA");
        assertArrayEquals(new TerdleGame.Result[] {NO_MATCH, EXACT_MATCH, NO_MATCH, NO_MATCH, NO_MATCH}, results);

        results = game.getGuessResults("TITLE");
        assertArrayEquals(new TerdleGame.Result[] {EXACT_MATCH, NO_MATCH, WRONG_LOCATION, NO_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults(GameTest.TEST_WORD);
        assertArrayEquals(new TerdleGame.Result[] {EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH}, results);
    }

    @Test
    public void getKeyboardResults() throws Exception {
        game.addGuess("above");
        Map<Character, TerdleGame.Result> expected = new HashMap<>();
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
}