package org.tbeerbower.services;

import org.tbeerbower.Player;
import org.tbeerbower.view.View;

import java.util.HashMap;
import java.util.Map;

public class PlayersService {
    private final Map<String, Player> players = new HashMap<>();
    private final View view;

    public PlayersService(View view) {
        this.view = view;
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
}
