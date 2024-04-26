package org.tbeerbower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseGame implements Game {

    private final String word;
    private final List<String> guesses = new ArrayList<>();


    // Game words
    private static final String[] WORDS = {"chair", "crate", "train", "allow", "about", "study"};

    public BaseGame() {
        this(getRandomWord());
    }

    protected BaseGame(String word) {
        this.word = word.toUpperCase();
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
    public void addGuess(String guess) {
        // TODO : add exceptions for overflow or invalid word
        if (guesses.size() < Game.MAX_GUESSES && guess.length() == Game.WORD_LENGTH && !isWin()) {
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

    private static String getRandomWord() {
        Random random = new Random(System.currentTimeMillis());
        int randomIndex = random.nextInt(WORDS.length);
        return WORDS[randomIndex];
    }
}
