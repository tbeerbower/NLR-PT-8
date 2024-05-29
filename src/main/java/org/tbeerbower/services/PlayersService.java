package org.tbeerbower.services;

import org.tbeerbower.model.Player;
import org.tbeerbower.model.PlayerComparator;
import org.tbeerbower.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayersService {
    private static final String PLAYERS_FILE_PATH = "terdle-players.dat";

    private Map<String, Player> players = new HashMap<>();
    private final View view;
    private final File file;

    public PlayersService(View view) {
        this(view, PLAYERS_FILE_PATH);
    }

    public PlayersService(View view, String playersFilePath) {
        this.view = view;
        this.file = new File(playersFilePath);
    }

    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }

    public Player getPlayer() {
        view.displayDivider();
        view.display("Enter player name: ");
        String playerName = view.getUserString();
        Player player = players.get(playerName);
        if (player == null) {
            player = new Player(playerName);
            players.put(playerName, player);
        }
        view.displayLine(String.format("Hello %s!", playerName));
        return player;
    }

    public void displayPlayers(Collection<Player> playerList, boolean showNumbers) {
        view.displayDivider();
        int i = 1;
        for (Player player : playerList) {
            String line = showNumbers ?
                    String.format("%d)\t%s", i++, player.toString()) :
                    player.toString();
            view.displayLine(line);
        }
    }
    public void displayPlayers() {
        displayPlayers(players.values(), false);
    }

    public void displayPlayers(PlayerComparator.Mode mode) {
        ArrayList<Player> playersList = new ArrayList<>(players.values());
        playersList.sort(new PlayerComparator(mode));
        displayPlayers(playersList, true);
    }

    public void savePlayers() {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file)) ){
            writer.writeObject(players);
        }  catch (IOException e) {
            view.displayLine("Can't save players: " + e.getMessage());
        }
    }

    public void loadPlayers() {
       try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
           players = (Map<String, Player>) reader.readObject();
       } catch (IOException | ClassNotFoundException e) {
           view.displayLine("Can't read players: " + e.getMessage());
       }
    }
}
