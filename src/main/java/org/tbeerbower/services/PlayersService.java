package org.tbeerbower.services;

import org.tbeerbower.model.Player;
import org.tbeerbower.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PlayersService {
    private Map<String, Player> players = new HashMap<>();
    private final View view;
    private final File file;

    public PlayersService(View view, String path) {
        this.view = view;
        this.file = new File(path);
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

    public void displayPlayers() {
        view.displayDivider();
        for (Player player : players.values()) {
            view.displayLine(player.toString());
        }
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
