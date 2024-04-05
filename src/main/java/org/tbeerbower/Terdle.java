package org.tbeerbower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Terdle {

    // Constants
    private static final int WORD_LENGTH = 5;
    private static final int MAX_GUESSES = 6;

    private static final String keyboard =
                    "QWERTYUIOP\n" +
                    "ASDFGHJKL\n" +
                    "ZXCVBNM\n";

    // ANSI color codes
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_BLACK = "\u001B[30m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String GRAY_BACKGROUND = "\u001B[47m";

    // Result Codes
    private static final int NO_MATCH = 0;
    private static final int WRONG_LOCATION = 1;
    private static final int EXACT_MATCH = 2;

    // backgrounds
    String[] backgrounds = {GRAY_BACKGROUND, YELLOW_BACKGROUND, GREEN_BACKGROUND};

    // Game words
    private static final String[] words = {"chair", "crate", "train", "allow", "about", "study"};

    // Instance variables
    private String word;
    private List<String> guesses = new ArrayList<>();

    public static void main(String[] args) {
        Terdle terdleGame = new Terdle();
        terdleGame.run();
    }

    public Terdle() {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        word = words[randomIndex].toUpperCase();
    }

    public void run() {
        System.out.println("Welcome to TErdle!");
        Scanner scanner  = new Scanner(System.in);

        int guessCount = 1;
        boolean win = false;

        while ( guessCount <= MAX_GUESSES && !win) {

            System.out.print("Please enter guess #" + guessCount + ": ");
            String guess = scanner.nextLine();
            guess = guess.toUpperCase();

            guesses.add(guess);

            printGuesses();

            ++guessCount;
            win = guess.equals( word );

            if (!win) {
                printKeyboard();
            }
        }
        if (win) {
            System.out.println("You won!!");
        } else {
            System.out.println("Sorry you didn't win.  The word is " + word + ".");
        }
    }

    private void printGuesses() {
        for (String guessToDisplay : guesses) {
            int[] results = checkGuess(guessToDisplay);
            printGuessResults(guessToDisplay, results);
        }
        System.out.println();
    }

    private void printKeyboard() {
        Map<Character, Integer> resultMap = new HashMap<>();
        for (String currentGuess : guesses) {
            int[] results = checkGuess(currentGuess);
            for (int i = 0; i < WORD_LENGTH; ++i) {
                Character guessChar = currentGuess.charAt(i);
                Integer resultCode = resultMap.get(guessChar);
                if (resultCode == null || results[i] > resultCode) {
                    resultMap.put(guessChar, results[i]);
                }
            }
        }
        for (int i = 0; i < keyboard.length(); ++i) {
            char keyboardChar = keyboard.charAt(i);
            Integer resultCode = resultMap.get(keyboardChar);
            printResultChar(keyboardChar, resultCode);
        }
        System.out.println();
    }

    private int[] checkGuess( String guess ) {

        List<Character> missedWordChars = new LinkedList<>();
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);
            if (wordChar != guessChar) {
                missedWordChars.add(wordChar);
            }
        }

        int[] resultCodes = new int[WORD_LENGTH];
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar) {
                resultCodes[i] = EXACT_MATCH;
            } else {
                int index = missedWordChars.indexOf(guessChar);
                if (index > -1){
                    resultCodes[i] = WRONG_LOCATION;
                    missedWordChars.remove(index);
                } else {
                    resultCodes[i] = NO_MATCH;
                }
            }
        }
        return resultCodes;
    }

    private void printGuessResults(String guess, int[] results) {
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char ch = guess.charAt(i);
            int resultCode = results[i];
            printResultChar(ch, resultCode);
        }
        System.out.println();
    }

    private void printResultChar(char guessChar, Integer resultCode) {
        if (resultCode == null) {
            System.out.format(" %c ",  guessChar);
        } else {
            String background = backgrounds[resultCode];
            System.out.format("%s%s %c %s", background, COLOR_BLACK, guessChar, COLOR_RESET);
        }
    }
}