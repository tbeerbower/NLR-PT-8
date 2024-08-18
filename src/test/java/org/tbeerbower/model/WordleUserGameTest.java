package org.tbeerbower.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.tbeerbower.model.WordleUserGame.ResultCode.EXACT_MATCH;
import static org.tbeerbower.model.WordleUserGame.ResultCode.NO_MATCH;
import static org.tbeerbower.model.WordleUserGame.ResultCode.WRONG_LOCATION;

public class WordleUserGameTest {

    private WordleUserGame game;

    @Before
    public void setup() {
        LocalDate now = LocalDate.now();
        game = new WordleUserGame(0, GameTest.TEST_WORD, now, 1, now);
    }

    @Test
    public void getGuessResults() {
        UserGame.Result[] results = game.getGuessResults("TASTE");
        assertArrayEquals(new UserGame.Result[] {EXACT_MATCH, NO_MATCH, EXACT_MATCH, EXACT_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults("ABOVE");
        assertArrayEquals(new UserGame.Result[] {NO_MATCH, NO_MATCH, NO_MATCH, NO_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults("ZEBRA");
        assertArrayEquals(new UserGame.Result[] {NO_MATCH, EXACT_MATCH, NO_MATCH, NO_MATCH, NO_MATCH}, results);

        results = game.getGuessResults("TITLE");
        assertArrayEquals(new UserGame.Result[] {EXACT_MATCH, NO_MATCH, WRONG_LOCATION, NO_MATCH, WRONG_LOCATION}, results);

        results = game.getGuessResults(GameTest.TEST_WORD);
        assertArrayEquals(new UserGame.Result[] {EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH, EXACT_MATCH}, results);
    }

    @Test
    public void getKeyboardResults() throws Exception {
        game.addGuess("above", List.of(GameTest.TEST_WORDS));
        Map<Character, UserGame.Result> expected = new HashMap<>();
        expected.put('A', NO_MATCH);
        expected.put('B', NO_MATCH);
        expected.put('O', NO_MATCH);
        expected.put('V', NO_MATCH);
        expected.put('E', WRONG_LOCATION);
        assertEquals(expected, game.getKeyboardResults());

        game.addGuess("taste", List.of(GameTest.TEST_WORDS));
        expected.put('T', EXACT_MATCH);
        expected.put('A', NO_MATCH);
        expected.put('S', EXACT_MATCH);
        expected.put('E', WRONG_LOCATION);
        assertEquals(expected, game.getKeyboardResults());

        game.addGuess("title", List.of(GameTest.TEST_WORDS));
        expected.put('I', NO_MATCH);
        expected.put('L', NO_MATCH);
        assertEquals(expected, game.getKeyboardResults());
    }
}