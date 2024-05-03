package org.tbeerbower;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGame implements Game {

    private final String word;

    private final transient List<String> validWords;

    private final List<String> guesses = new ArrayList<>();

    public BaseGame(String word, List<String> validWords) {
        this.word = word.toUpperCase();
        this.validWords = validWords;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public List<String> getGuesses() {
        return guesses;
    }

    @Override
    public void addGuess(String guess) throws InvalidGuessException {
        // TODO : add exceptions for overflow
        if (guess.length() != Game.WORD_LENGTH) {
            throw new InvalidGuessException(guess,
                    String.format("Required to be %d characters long.", Game.WORD_LENGTH));
        }
        String lowerCaseGuess = guess.toLowerCase();
        if (!validWords.contains(lowerCaseGuess)) {
            throw new InvalidGuessException(guess, "Not a Wordle word.");
        }
        if (guesses.size() < Game.MAX_GUESSES && !isWin()) {
            guesses.add(guess.toUpperCase());
        }
    }

    @Override
    public boolean isWin() {
        if (!guesses.isEmpty()) {
            return word.equals(guesses.get(guesses.size() - 1));
        }
        return false;
    }

    @Override
    public boolean isLoss() {
        return guesses.size() == MAX_GUESSES && !isWin();
    }
}
