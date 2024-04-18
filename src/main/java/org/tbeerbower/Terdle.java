package org.tbeerbower;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Terdle {

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

            System.out.println("1) Play Wordle game");
            System.out.println("2) Play Wordle Peaks game");
            System.out.println("3) Change player");
            System.out.println("4) Exit");
            System.out.println("Please enter your choice.");

            // TODO : validate input
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1 || choice == 2) {
                System.out.println("--------------------------");
                Game currentGame = choice == 1 ? new WordleGame() : new WordlePeaksGame();

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

            } else if (choice == 3) {
                currentPlayer = null;
            } else if (choice == 4) {
                System.out.println("Thanks for playing " + currentPlayer.getName() + "!");
                run = false;
            } else {
                System.out.println(choice + " is not a valid option!");
            }
        }
    }

    private void printGuesses(Game game) {
        for (String guessToDisplay : game.getGuesses()) {
            int[] results = game.getGuessResults(guessToDisplay);
            printGuessResults(game, guessToDisplay, results);
        }
        System.out.println();
    }

    private void printKeyboard(Game game) {
        Map<Character, Integer> resultMap = game.getKeyboardResults();
        for (int i = 0; i < View.KEYBOARD.length(); ++i) {
            char keyboardChar = View.KEYBOARD.charAt(i);
            Integer resultCode = resultMap.get(keyboardChar);
            printResultChar(game, keyboardChar, resultCode);
        }
        System.out.println();
    }

    private void printGuessResults(Game game, String guess, int[] results) {
        for ( int i = 0; i < guess.length(); ++i) {
            char ch = guess.charAt(i);
            int resultCode = results[i];
            printResultChar(game, ch, resultCode);
        }
        System.out.println();
    }

    private void printResultChar(Game game, char guessChar, Integer resultCode) {
        if (resultCode == null) {
            System.out.format(" %c ",  guessChar);
        } else {
            String background = game.getGameColors(resultCode);
            System.out.format("%s%s %c %s", background, View.COLOR_BLACK, guessChar, View.COLOR_RESET);
        }
    }
}