package org.tbeerbower.daos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.tbeerbower.model.GameTest;
import org.tbeerbower.model.GameType;
import org.tbeerbower.model.UserGame;
import org.tbeerbower.model.WordlePeaksUserGame;
import org.tbeerbower.model.WordleUserGame;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class JdbcUserGameDaoTest {

    private JdbcTemplate jdbcTemplate;

    private JdbcUserGameDao jdbcUserGameDao;

    private UserGame wordleUserGame;
    private UserGame peaksUserGame;

    @Before
    public void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        jdbcUserGameDao = new JdbcUserGameDao(jdbcTemplate);
        wordleUserGame = new WordleUserGame(1, GameTest.TEST_WORD, LocalDate.now(), 1, LocalDate.now());
        peaksUserGame = new WordlePeaksUserGame(2, GameTest.TEST_WORD, LocalDate.now(), 1, LocalDate.now());
    }

    @Test
    public void getUserGames() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any()))
                .thenReturn(Arrays.asList(wordleUserGame, peaksUserGame));

        List<UserGame> result = jdbcUserGameDao.getUserGames();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getUserGamesByUserId_withDateAndType() {
        LocalDate date = LocalDate.now();
        GameType type = GameType.WORDLE;

        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any(), eq(1), eq(date), eq(0)))
                .thenReturn(Collections.singletonList(wordleUserGame));

        List<UserGame> result = jdbcUserGameDao.getUserGamesByUserId(1, date, type);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getUserGamesByUserId_withDate() {
        LocalDate date = LocalDate.now();

        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any(), eq(1), eq(date)))
                .thenReturn(Collections.singletonList(wordleUserGame));

        List<UserGame> result = jdbcUserGameDao.getUserGamesByUserId(1, date, null);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getUserGamesByUserId_withType() {
        GameType type = GameType.WORDLE;

        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any(), eq(1), eq(0)))
                .thenReturn(Collections.singletonList(wordleUserGame));

        List<UserGame> result = jdbcUserGameDao.getUserGamesByUserId(1, null, type);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getUserGamesByUserId_withoutDateAndType() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any(), eq(1)))
                .thenReturn(Collections.singletonList(wordleUserGame));

        List<UserGame> result = jdbcUserGameDao.getUserGamesByUserId(1, null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getUserGameByUserIdAndGameId() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any(), eq(1), eq(1)))
                .thenReturn(Collections.singletonList(wordleUserGame));

        UserGame result = jdbcUserGameDao.getUserGameByUserIdAndGameId(1, 1);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    @Test
    public void createUserGame() {
        List<String> guesses = Arrays.asList(GameTest.TEST_WORDS[0], GameTest.TEST_WORDS[1], GameTest.TEST_WORD);
        wordleUserGame.setGuesses(guesses);

        when(jdbcTemplate.update(anyString(), eq(1), eq(1), any(LocalDate.class), eq(guesses.size()),
                eq(GameTest.TEST_WORDS[0]), eq(GameTest.TEST_WORDS[1]), eq(GameTest.TEST_WORD), isNull(), isNull(), isNull(), eq(true)))
                .thenReturn(1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any(), eq(1), eq(1)))
                .thenReturn(Collections.singletonList(wordleUserGame));

        UserGame result = jdbcUserGameDao.createUserGame(1, 1, wordleUserGame);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    @Test
    public void updateUserGame() {
        List<String> guesses = Arrays.asList(GameTest.TEST_WORDS[0], GameTest.TEST_WORDS[1], GameTest.TEST_WORD);
        wordleUserGame.setGuesses(guesses);

        when(jdbcTemplate.update(anyString(), eq(guesses.size()),
                eq(GameTest.TEST_WORDS[0]), eq(GameTest.TEST_WORDS[1]), eq(GameTest.TEST_WORD), isNull(), isNull(), isNull(),
                eq(true), eq(1), eq(1)))
                .thenReturn(1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserGame>>any(), eq(1), eq(1)))
                .thenReturn(Collections.singletonList(wordleUserGame));

        UserGame result = jdbcUserGameDao.updateUserGame(1, 1, wordleUserGame);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }
}
