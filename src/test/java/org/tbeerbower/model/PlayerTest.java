package org.tbeerbower.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest extends GameTest {

    private static final String PLAYER_NAME = "Mario";
    
    private Player player;
    
    @Before
    public void setup() {
        player = new Player(PLAYER_NAME);    
    }
    
    @Test
    public void getName() {
        assertEquals(PLAYER_NAME, player.getName());
    }

    @Test
    public void addGame() throws Exception {
        player.addGame(getWinningGame(TerdleGame.MAX_GUESSES));
        player.addGame(getWinningGame(TerdleGame.MAX_GUESSES));
        player.addGame(getLosingGame());
        player.addGame(getLosingGame());
        assertEquals(4, player.getWins() + player.getLosses());
    }

    @Test
    public void getWins() throws Exception {
        assertEquals(0, player.getWins());
        player.addGame(getWinningGame(TerdleGame.MAX_GUESSES));
        player.addGame(getLosingGame());
        player.addGame(getWinningGame(TerdleGame.MAX_GUESSES));
        player.addGame(getLosingGame());
        assertEquals(2, player.getWins());
    }

    @Test
    public void getLosses() throws Exception {
        assertEquals(0, player.getLosses());
        player.addGame(getWinningGame(TerdleGame.MAX_GUESSES));
        player.addGame(getLosingGame());
        player.addGame(getWinningGame(TerdleGame.MAX_GUESSES));
        player.addGame(getLosingGame());
        assertEquals(2, player.getLosses());  
    }

    @Test
    public void getAverageScore() throws Exception {
        assertEquals(0, player.getLosses());
        player.addGame(getWinningGame(3));
        player.addGame(getLosingGame());
        player.addGame(getWinningGame(4));
        player.addGame(getLosingGame());
        assertEquals(5.25, player.getAverageScore(), 0.01);
    }
}