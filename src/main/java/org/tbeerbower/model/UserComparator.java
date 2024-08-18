package org.tbeerbower.model;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    public enum Mode {
        WINS_ASC,
        WINS_DESC,
        AVG_GUESSES_ASC,
        AVG_GUESSES_DESC
    }

    private final Mode mode;

    public UserComparator(Mode mode) {
        this.mode = mode;
    }

    @Override
    public int compare(User user1, User user2) {
        switch (mode) {
            case WINS_ASC:
                return Integer.compare(user1.getWins(), user2.getWins());
            case WINS_DESC:
                return Integer.compare(user2.getWins(), user1.getWins());
            case AVG_GUESSES_ASC:
                return Double.compare(user1.getAverageScore(), user2.getAverageScore());
            case AVG_GUESSES_DESC:
                return Double.compare(user2.getAverageScore(), user1.getAverageScore());
        }
        return 0;
    }
}
