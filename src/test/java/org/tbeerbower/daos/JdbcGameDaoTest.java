package org.tbeerbower.daos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.tbeerbower.model.Game;
import org.tbeerbower.model.GameTest;
import org.tbeerbower.model.GameType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JdbcGameDaoTest {

    private JdbcTemplate jdbcTemplate;
    private JdbcGameDao jdbcGameDao;
    private Game game1;
    private Game game2;

    @Before
    public void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        jdbcGameDao = new JdbcGameDao(jdbcTemplate);
        game1 = new Game(1, GameTest.TEST_WORD, LocalDate.of(2023, 8, 15), GameType.WORDLE);
        game2 = new Game(2, GameTest.TEST_WORDS[0], LocalDate.of(2023, 8, 16), GameType.WORDLE_PEAKS);
    }

    @Test
    public void getGames() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Game>>any()))
                .thenReturn(Arrays.asList(game1, game2));

        List<Game> games = jdbcGameDao.getGames();

        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    public void getGameById() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Game>>any(), eq(1)))
                .thenReturn(Collections.singletonList(game1));

        Game game = jdbcGameDao.getGameById(1);

        assertNotNull(game);
        assertEquals(GameTest.TEST_WORD, game.getWord());
    }

    @Test
    public void getGamesByType() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Game>>any(), eq(GameType.WORDLE.ordinal())))
                .thenReturn(Collections.singletonList(game1));

        List<Game> games = jdbcGameDao.getGamesByType(GameType.WORDLE);

        assertNotNull(games);
        assertEquals(1, games.size());
        assertEquals(GameType.WORDLE, games.get(0).getGameType());
    }

    @Test
    public void getGamesByDateAndType() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Game>>any(), eq(game1.getGameDate()), eq(GameType.WORDLE.ordinal())))
                .thenReturn(Collections.singletonList(game1));

        List<Game> games = jdbcGameDao.getGamesByDateAndType(game1.getGameDate(), GameType.WORDLE);

        assertNotNull(games);
        assertEquals(1, games.size());
        assertEquals(GameTest.TEST_WORD, games.get(0).getWord());
    }

    @Test
    public void getGamesByWordAndType() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Game>>any(),
                eq(GameTest.TEST_WORD), eq(GameType.WORDLE.ordinal())))
                .thenReturn(Collections.singletonList(game1));

        List<Game> games = jdbcGameDao.getGamesByWordAndType(GameTest.TEST_WORD, GameType.WORDLE);

        assertNotNull(games);
        assertEquals(1, games.size());
        assertEquals(GameTest.TEST_WORD, games.get(0).getWord());
    }

    @Test
    public void createGame() {
        when(jdbcTemplate.queryForObject(anyString(), eq(int.class), anyString(), any(LocalDate.class), anyInt()))
                .thenReturn(1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Game>>any(), eq(1)))
                .thenReturn(Collections.singletonList(game1));

        Game createdGame = jdbcGameDao.createGame(game1);

        assertNotNull(createdGame);
        assertEquals(GameTest.TEST_WORD, createdGame.getWord());
    }

    @Test
    public void updateGame() {
        when(jdbcTemplate.update(anyString(), anyString(), any(LocalDate.class), anyInt(), eq(1)))
                .thenReturn(1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Game>>any(), eq(1)))
                .thenReturn(Collections.singletonList(game1));

        Game updatedGame = jdbcGameDao.updateGame(1, game1);

        assertNotNull(updatedGame);
        assertEquals(GameTest.TEST_WORD, updatedGame.getWord());
    }
}
