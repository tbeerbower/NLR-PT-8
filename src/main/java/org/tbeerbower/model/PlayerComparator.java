package org.tbeerbower.model;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    public enum Mode {
        WINS_ASC,
        WINS_DESC,
        AVG_GUESSES_ASC,
        AVG_GUESSES_DESC
    }

    private final Mode mode;

    public PlayerComparator(Mode mode) {
        this.mode = mode;
    }

    @Override
    public int compare(Player player1, Player player2) {
        switch (mode) {
            case WINS_ASC:
                return Integer.compare(player1.getWins(), player2.getWins());
            case WINS_DESC:
                return Integer.compare(player2.getWins(),player1.getWins());
            case AVG_GUESSES_ASC:
                return Double.compare(player1.getAverageScore(), player2.getAverageScore());
            case AVG_GUESSES_DESC:
                return Double.compare(player2.getAverageScore(), player1.getAverageScore());
        }
        return 0;
    }
}
