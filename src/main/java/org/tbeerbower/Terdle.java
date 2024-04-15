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


    // backgrounds
    String[] backgrounds = {GRAY_BACKGROUND, YELLOW_BACKGROUND, GREEN_BACKGROUND};

    // Instance variables
    private final Map<String, Player> players = new HashMap<>();

    public static void main(String[] args) {
        Terdle terdleGame = new Terdle();
        terdleGame.run();
    }

    public Terdle() {
    }

    public void run() {
        System.out.println("Welcome to TErdle!");
        Scanner scanner  = new Scanner(System.in);
        boolean run = true;
        Player currentPlayer = null;


        while(run) {

            if (currentPlayer == null) {
                System.out.println("--------------------------");
                System.out.print("Enter player name: ");
                String playerName = scanner.nextLine();
                currentPlayer = players.get(playerName);
                if (currentPlayer == null) {
                    currentPlayer = new Player(playerName);
                    players.put(playerName, currentPlayer);
                }
                System.out.println("Hello " + playerName + "!");
            }
            System.out.println("--------------------------");

            System.out.println("1) Play game");
            System.out.println("2) Change player");
            System.out.println("3) Exit");
            System.out.println("Please enter your choice.");

            // TODO : validate input
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                System.out.println("--------------------------");
                Game currentGame = new Game();

                int guessCount = 1;
                boolean win = false;


                while (guessCount <= Game.MAX_GUESSES && !win) {

                    System.out.print("Please enter guess #" + guessCount + ": ");
                    String guess = scanner.nextLine();
                    guess = guess.toUpperCase();

                    currentGame.addGuess(guess);

                    printGuesses(currentGame);

                    ++guessCount;
                    win = guess.equals(currentGame.getWord());

                    if (!win) {
                        printKeyboard(currentGame);
                    }
                }
                if (win) {
                    System.out.println("You won!!");
                } else {
                    System.out.println("Sorry you didn't win.  The word is " + currentGame.getWord() + ".");
                }
                currentPlayer.addGame(currentGame);
                System.out.println("Player " + currentPlayer.getName() + ": " + currentPlayer.getWins() + " wins, " +
                        currentPlayer.getLosses() + " losses, average score " + currentPlayer.getAverageScore());

            } else if (choice == 2) {
                currentPlayer = null;
            } else if (choice == 3) {
                System.out.println("Thanks for playing " + currentPlayer.getName() + "!");
                run = false;
            } else {
                System.out.println(choice + " is not a valid option!");
            }
        }
    }

    private void printGuesses(Game game) {
        for (String guessToDisplay : game.getGuesses()) {
            int[] results = game.checkGuess(guessToDisplay);
            printGuessResults(guessToDisplay, results);
        }
        System.out.println();
    }

    private void printKeyboard(Game game) {
        Map<Character, Integer> resultMap = new HashMap<>();
        for (String currentGuess : game.getGuesses()) {
            int[] results = game.checkGuess(currentGuess);
            for (int i = 0; i < currentGuess.length(); ++i) {
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


    private void printGuessResults(String guess, int[] results) {
        for ( int i = 0; i < guess.length(); ++i) {
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