package org.tbeerbower;

import java.util.Random;
import java.util.Scanner;

public class Terdle {

    // Constants
    private static final int WORD_LENGTH = 5;
    private static final int MAX_GUESSES = 6;

    // Result Codes
    private static final int NO_MATCH = 0;
    private static final int EXACT_MATCH = 1;
    private static final int WRONG_LOCATION = 2;

    // Game words
    private static final String[] words = {"chair", "crate", "train", "allow", "about", "study"};

    // ANSI color codes
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_BLACK = "\u001B[30m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String GRAY_BACKGROUND = "\u001B[47m";

    // Member variables
    private String word;

    public static void main(String[] args) {
        Terdle terdleGame = new Terdle();
        terdleGame.run();
    }

    public Terdle() {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        word = words[randomIndex];
    }

    public void run() {
        System.out.println("Welcome to TErdle!");
        Scanner scanner  = new Scanner(System.in);

        int guessCount = 1;
        boolean win = false;

        while ( guessCount <= MAX_GUESSES && !win) {

            System.out.print("Please enter guess #" + guessCount + ": ");
            String guess = scanner.nextLine();
            guess = guess.toLowerCase();

            int[] results = checkGuess(guess);
            printGuessResults(guess, results);

            ++guessCount;
            win = guess.equals( word );
        }
        if (win) {
            System.out.println("You won!!");
        } else {
            System.out.println("Sorry you didn't win.  The word is " + word + ".");
        }
    }

    private int[] checkGuess( String guess ) {

        String missedWordChars = "";
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);
            if (wordChar != guessChar) {
                missedWordChars += wordChar;
            }
        }

        int[] resultCodes = new int[WORD_LENGTH];
        for ( int i = 0; i < WORD_LENGTH; ++i) {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar) {
                resultCodes[i] = EXACT_MATCH;
            } else {
                if (missedWordChars.contains(Character.toString(guessChar))){
                    resultCodes[i] = WRONG_LOCATION;
                    missedWordChars = missedWordChars.replace(Character.toString(guessChar), "");
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
            String background = GRAY_BACKGROUND;
            if (resultCode == EXACT_MATCH) {
                background = GREEN_BACKGROUND;
            } else if ( resultCode == WRONG_LOCATION) {
                background = YELLOW_BACKGROUND;
            }
            printGuessChar(ch, background);
        }
        System.out.println();
    }

    private void printGuessChar(char guessChar, String background) {
        String upperCaseGuessChar = Character.toString(guessChar).toUpperCase();
        String formattedGuessChar =
                String.format("%s%s %s %s", background, COLOR_BLACK, upperCaseGuessChar, COLOR_RESET);
        System.out.print(formattedGuessChar);
    }
}