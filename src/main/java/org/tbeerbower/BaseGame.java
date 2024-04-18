package org.tbeerbower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseGame implements Game {

    private String word;
    private List<String> guesses;


    // Game words
    private static final String[] words = {"chair", "crate", "train", "allow", "about", "study"};

    public BaseGame() {
        Random random = new Random(System.currentTimeMillis());
        int randomIndex = random.nextInt(words.length);
        word = words[randomIndex].toUpperCase();
        guesses = new ArrayList<>();
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
        guesses.add(guess);
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
