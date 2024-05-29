package org.tbeerbower;

import org.tbeerbower.model.InvalidGuessException;
import org.tbeerbower.model.Player;
import org.tbeerbower.model.TerdleGame;
import org.tbeerbower.model.WordleGame;
import org.tbeerbower.model.WordlePeaksGame;
import org.tbeerbower.services.GameService;
import org.tbeerbower.services.PlayersService;
import org.tbeerbower.view.View;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Utility class to generate test players data
 */
public class PlayerDataGenerator {
    private static final String PLAYERS_FILE_PATH = "test-terdle-players.dat";

    public static void main(String[] args) {
        Player player1 = new Player("Rob");
        Player player2 = new Player("Tom");
        Player player3 = new Player("Jeanne");
        Player player4 = new Player("Geoff");

        View view = new View(new Scanner(System.in));
        GameService.initWords(view);
        PlayersService service = new PlayersService(view, PLAYERS_FILE_PATH);

        service.addPlayer(player1);
        service.addPlayer(player2);
        service.addPlayer(player3);
        service.addPlayer(player4);

        try {
            playGames(player1);
            playGames(player2);
            playGames(player3);
            playGames(player4);
        } catch (InvalidGuessException e) {
            view.displayLine("Can't play games: " + e.getMessage());
        }
        service.savePlayers();
    }

    private static void playGames(Player player) throws InvalidGuessException {

        Random random = new Random(System.currentTimeMillis());

        int numberGames = random.nextInt(20) + 80;

        for (int i = 0; i < numberGames; ++i) {

            List<String> validGuesses = GameService.getValidGuesses();
            List<String> words = GameService.getWords();

            String word = words.get(random.nextInt(words.size()));

            TerdleGame game = random.nextInt(2) == 0 ?
                    new WordleGame(word, validGuesses) :
                    new WordlePeaksGame(word, validGuesses);

            int numberGuesses = random.nextInt(TerdleGame.MAX_GUESSES) + 1;
            for (int j = 0; j < numberGuesses; ++j) {
                String guess = validGuesses.get(random.nextInt(validGuesses.size()));
                game.addGuess(guess);
            }
            if(numberGuesses < TerdleGame.MAX_GUESSES) {
                game.addGuess(word);
            }
            player.addGame(game);
        }


    }

}
