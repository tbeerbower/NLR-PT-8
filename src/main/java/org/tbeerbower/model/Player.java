package org.tbeerbower.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private List<TerdleGame> games = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addGame(TerdleGame game) {
        games.add(game);
    }

    public int getWins() {
        int wins = 0;
        for (TerdleGame game : games) {
            if (game.isWin()) {
                ++wins;
            }
        }
        return wins;
    }

    public int getLosses() {
        int losses = 0;
        for (TerdleGame game : games) {
            if (game.isLoss()) {
                ++losses;
            }
        }
        return losses;
    }

    public double getAverageScore() {
        int totalGuesses = 0;
        int gamesPlayed = 0;
        for (TerdleGame game : games) {
            if (game.isWin()) {
               totalGuesses += game.getGuesses().size();
               ++gamesPlayed;
            } else if(game.isLoss()) {
                totalGuesses += WordleGame.TOTAL_GUESSES_FOR_LOSS;
                ++gamesPlayed;
            }
        }
        return gamesPlayed == 0 ? 0 : totalGuesses / (double) gamesPlayed;
    }

    @Override
    public String toString() {
        return String.format("%s: %d wins, %d losses, average score %s",
                getName(), getWins(), getLosses(), getAverageScore());
    }
}
