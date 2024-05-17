package org.tbeerbower.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTerdleGame implements TerdleGame {

    private final String word;

    private final transient List<String> validWords;

    private final List<String> guesses = new ArrayList<>();

    public BaseTerdleGame(String word, List<String> validWords) {
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
        if (guess.length() != TerdleGame.WORD_LENGTH) {
            throw new InvalidGuessException(guess,
                    String.format("Required to be %d characters long.", TerdleGame.WORD_LENGTH));
        }
        String lowerCaseGuess = guess.toLowerCase();
        if (!validWords.contains(lowerCaseGuess)) {
            throw new InvalidGuessException(guess, "Not a Wordle word.");
        }
        if (guesses.size() >= TerdleGame.MAX_GUESSES || isWin()) {
            throw new InvalidGuessException(guess, "Can't add a guess to a completed game.");
        }
        guesses.add(guess.toUpperCase());
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
