package org.tbeerbower.model;

import java.time.LocalDate;

public class Game {

    private int gameId;
    private String word;
    private LocalDate gameDate;
    private GameType type = GameType.WORDLE;

    public Game() {
    }

    public Game(int gameId, String word, LocalDate gameDate, GameType type) {
        this.gameId = gameId;
        this.word = word;
        this.gameDate = gameDate;
        this.type = type;
    }

    public Game(String word, LocalDate gameDate, GameType type) {
        this(0, word, gameDate, type);
    }

    public int getGameId() {
        return gameId;
    }

    public String getWord() {
        return word;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public GameType getGameType() {
        return type;
    }
}
