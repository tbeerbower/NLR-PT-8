package org.tbeerbower.dao;

import org.tbeerbower.model.Game;
import org.tbeerbower.model.GameType;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO class for interacting with the game information in the data store.
 */
public interface GameDao {

    /**
     * Get all games from the datastore.
     *
     * @return List of all Game objects, or an empty list if no Games are found.
     */
    List<Game> getGames();

    /**
     * Get a game from the datastore with the specified id.
     * If the id is not found, returns null.
     *
     * @param gameId The id of the game to return.
     * @return The matching Game object.
     */
    Game getGameById(int gameId);

    /**
     * Get all games from the datastore with the specified type.
     *
     * @param type The type of the games to return.
     * @return List of all matching Game objects, or an empty list if no matching Games are found.
     */
    List<Game> getGamesByType(GameType type);

    /**
     * Get all games from the datastore with the specified date and type.
     *
     * @param date The date of the games to return.
     * @param type The type of the games to return.
     * @return List of all matching Game objects, or an empty list if no matching Games are found.
     */
    List<Game> getGamesByDateAndType(LocalDate date, GameType type);

    /**
     * Get all games from the datastore with the specified word and type.
     *
     * @param word The word of the games to return.
     * @param type The type of the games to return.
     * @return List of all matching Game objects, or an empty list if no matching Games are found.
     */
    List<Game> getGamesByWordAndType(String word, GameType type);

    /**
     * Adds a new game to the datastore.
     *
     * @param newGame the game to add
     * @return The new Game object with its new id filled in.
     */
    Game createGame(Game newGame);

    /**
     * Updates the game information.
     * @param modifiedGame the game data to update
     * @return the updated Game object
     */
    Game updateGame(int gameId, Game modifiedGame);
}
