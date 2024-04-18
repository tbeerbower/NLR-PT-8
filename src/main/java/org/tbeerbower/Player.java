package org.tbeerbower;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Game> games = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public int getWins() {
        int wins = 0;
        for (Game game : games) {
            if (game.isWin()) {
                ++wins;
            }
        }
        return wins;
    }

    public int getLosses() {
        int losses = 0;
        for (Game game : games) {
            if (game.isLoss()) {
                ++losses;
            }
        }
        return losses;
    }

    public double getAverageScore() {
        int totalGuesses = 0;
        int gamesPlayed = 0;
        for (Game game : games) {
            if (game.isWin()) {
               totalGuesses += game.getGuesses().size();
               ++gamesPlayed;
            } else if(game.isLoss()) {
                totalGuesses += WordleGame.TOTAL_GUESSES_FOR_LOSS;
                ++gamesPlayed;
            }
        }
        return totalGuesses / (double) gamesPlayed;
    }

}
