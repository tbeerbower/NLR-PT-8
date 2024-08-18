package org.tbeerbower.model;

import org.tbeerbower.exception.InvalidGuessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserGame {

    // Constants
    int WORD_LENGTH = 5;
    int MAX_GUESSES = 6;
    int TOTAL_GUESSES_FOR_LOSS = 7;

    int getGameId();
    String getWord();
    GameType getGameType();
    int getUserId();
    LocalDate getPlayedDate();
    List<String> getGuesses();
    void setGuesses(List<String> guesses);
    void addGuess(String guess, List<String> validGuesses) throws InvalidGuessException;
    Result[] getGuessResults(String guess);
    Map<Character, Result> getKeyboardResults();
    boolean isWin();
    boolean isLoss();

    /**
     * Result interface represents the result of matching a character from a guess to the game word.
     */
    interface Result {
        int getValue();
        String getColor();
    }
}
