package org.tbeerbower.services;

import org.springframework.stereotype.Service;
import org.tbeerbower.dao.GameDao;
import org.tbeerbower.dao.UserGameDao;
import org.tbeerbower.model.Game;
import org.tbeerbower.model.GameType;
import org.tbeerbower.model.UserGame;
import org.tbeerbower.exception.InvalidGuessException;
import org.tbeerbower.model.User;
import org.tbeerbower.model.WordleUserGame;
import org.tbeerbower.model.WordlePeaksUserGame;
import org.tbeerbower.utils.ScannerProvider;
import org.tbeerbower.utils.WordReader;
import org.tbeerbower.view.View;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {
    // Constants
    private static final String RESOURCE_DIR = "src/main/resources";
    private static List<String> WORDS;
    private static List<String> VALID_GUESSES;

    private final GameDao gameDao;
    private final UserGameDao userGameDao;


    public GameService(GameDao gameDao, UserGameDao userGameDao) {
        this.gameDao = gameDao;
        this.userGameDao = userGameDao;
    }

    public static void initWords(View view) {
        WordReader reader = new WordReader(view, new ScannerProvider());
        WORDS = reader.getWords(RESOURCE_DIR + "/words.txt");
        VALID_GUESSES = reader.getWords(RESOURCE_DIR + "/guesses.txt");
        VALID_GUESSES.addAll(WORDS);
    }

    public static List<String> getWords() {
        return WORDS;
    }

    public void playGame(View view, User user, GameType type) {
        int guessCount = 1;
        boolean win = false;

        String word = getRandomWord().toUpperCase();

        LocalDate now = LocalDate.now();
        Game game = new Game(0, word, now, type);
        game = gameDao.createGame(game);
        UserGame userGame = type == GameType.WORDLE ? new WordleUserGame(game, user.getId(), now) :
                new WordlePeaksUserGame(game, user.getId(), now);
        userGame = userGameDao.createUserGame(user.getId(), game.getGameId(), userGame);

        view.displayDivider();
        while (guessCount <= UserGame.MAX_GUESSES && !win) {
            promptForGuess(view, userGame, guessCount);
            displayGuesses(view, userGame);
            win = userGame.isWin();
            if (!win) {
                displayKeyboard(view, userGame);
            }
            ++guessCount;
        }
        if (win) {
            view.displayLine("You won!!");
        } else {
            view.displayLine(String.format("Sorry you didn't win.  The word is %s.", userGame.getWord()));
        }
        userGameDao.updateUserGame(user.getId(), game.getGameId(), userGame);
    }

    private void promptForGuess(View view, UserGame game, int guessCount) {
        while (true) {
            view.display(String.format("Please enter guess #%d: ", guessCount));
            String guess = view.getUserString();
            try {
                game.addGuess(guess, VALID_GUESSES);
                return;
            } catch (InvalidGuessException e) {
                view.displayLine(String.format("'%s' is not a valid guess word! %s", guess, e.getMessage()));
            }
        }
    }

    private void displayGuesses(View view, UserGame game) {
        for (String guessToDisplay : game.getGuesses()) {
            UserGame.Result[] results = game.getGuessResults(guessToDisplay);
            displayGuessResults(view, guessToDisplay, results);
        }
        view.displayLine("");
    }

    private void displayKeyboard(View view, UserGame game) {
        Map<Character, UserGame.Result> resultMap = game.getKeyboardResults();
        for (int i = 0; i < View.KEYBOARD.length(); ++i) {
            char keyboardChar = View.KEYBOARD.charAt(i);
            displayResultChar(view, keyboardChar, resultMap.get(keyboardChar));
        }
        view.displayLine("");
    }

    private void displayGuessResults(View view, String guess, UserGame.Result[] results) {
        for (int i = 0; i < guess.length(); ++i) {
            char ch = guess.charAt(i);
            displayResultChar(view, ch, results[i]);
        }
        view.displayLine("");
    }

    private void displayResultChar(View view, char guessChar, UserGame.Result resultCode) {
        String color = resultCode == null ? null : resultCode.getColor();
        view.display(String.format(" %c ", guessChar), color);
    }

    private String getRandomWord() {
        Random random = new Random(System.currentTimeMillis());
        List<String> words = GameService.getWords();

        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }
}
