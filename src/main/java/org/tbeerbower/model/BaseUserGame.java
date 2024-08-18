package org.tbeerbower.model;

import org.tbeerbower.exception.InvalidGuessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseUserGame extends Game implements UserGame {

    private final int userId;
    private final LocalDate playedDate;
    private List<String> guesses = new ArrayList<>();

    public BaseUserGame(Game game, int userId, LocalDate playedDate) {
        this(game.getGameId(), game.getWord(), game.getGameType(), game.getGameDate(), userId, playedDate);
    }

    public BaseUserGame(int gameId, String word, GameType type, LocalDate createdDate, int userId, LocalDate playedDate) {
        super(gameId, word, createdDate, type);
        this.userId = userId;
        this.playedDate = playedDate;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public LocalDate getPlayedDate() {
        return playedDate;
    }

    @Override
    public List<String> getGuesses() {
        return guesses;
    }

    @Override
    public void setGuesses(List<String> guesses) {
        this.guesses = guesses;
    }

    @Override
    public void addGuess(String guess, List<String> validGuesses) throws InvalidGuessException {
        if (guess.length() != UserGame.WORD_LENGTH) {
            throw new InvalidGuessException(guess,
                    String.format("Required to be %d characters long.", UserGame.WORD_LENGTH));
        }
        String lowerCaseGuess = guess.toLowerCase();
        if (!validGuesses.contains(lowerCaseGuess)) {
            throw new InvalidGuessException(guess, "Not a valid word.");
        }
        if (guesses.size() >= UserGame.MAX_GUESSES || isWin()) {
            throw new InvalidGuessException(guess, "Can't add a guess to a completed game.");
        }
        guesses.add(guess.toUpperCase());
    }

    @Override
    public boolean isWin() {
        if (!guesses.isEmpty()) {
            return getWord().equals(guesses.get(guesses.size() - 1));
        }
        return false;
    }

    @Override
    public boolean isLoss() {
        return guesses.size() == MAX_GUESSES && !isWin();
    }
}
