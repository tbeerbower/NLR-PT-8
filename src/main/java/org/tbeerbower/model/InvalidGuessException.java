package org.tbeerbower.model;

public class InvalidGuessException extends Exception {
    private final String guess;

    public InvalidGuessException(String guess, String message) {
        super(message);
        this.guess = guess;
    }

    public String getGuess() {
        return guess;
    }
}
