package org.tbeerbower.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest extends GameTest {

    private static final String PLAYER_NAME = "Mario";
    
    private User user;
    
    @Before
    public void setup() {
        user = new User(PLAYER_NAME);
    }
    
    @Test
    public void getName() {
        assertEquals(PLAYER_NAME, user.getUsername());
    }

    @Test
    public void addGame() throws Exception {
        user.addGame(getWinningGame(UserGame.MAX_GUESSES));
        user.addGame(getWinningGame(UserGame.MAX_GUESSES));
        user.addGame(getLosingGame());
        user.addGame(getLosingGame());
        assertEquals(4, user.getWins() + user.getLosses());
    }

    @Test
    public void getWins() throws Exception {
        assertEquals(0, user.getWins());
        user.addGame(getWinningGame(UserGame.MAX_GUESSES));
        user.addGame(getLosingGame());
        user.addGame(getWinningGame(UserGame.MAX_GUESSES));
        user.addGame(getLosingGame());
        assertEquals(2, user.getWins());
    }

    @Test
    public void getLosses() throws Exception {
        assertEquals(0, user.getLosses());
        user.addGame(getWinningGame(UserGame.MAX_GUESSES));
        user.addGame(getLosingGame());
        user.addGame(getWinningGame(UserGame.MAX_GUESSES));
        user.addGame(getLosingGame());
        assertEquals(2, user.getLosses());
    }

    @Test
    public void getAverageScore() throws Exception {
        assertEquals(0, user.getLosses());
        user.addGame(getWinningGame(3));
        user.addGame(getLosingGame());
        user.addGame(getWinningGame(4));
        user.addGame(getLosingGame());
        assertEquals(5.25, user.getAverageScore(), 0.01);
    }
}