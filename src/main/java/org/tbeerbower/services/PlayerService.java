package org.tbeerbower.services;

import org.springframework.stereotype.Service;
import org.tbeerbower.dao.UserDao;
import org.tbeerbower.dao.UserGameDao;
import org.tbeerbower.exception.DaoException;
import org.tbeerbower.model.User;
import org.tbeerbower.model.UserComparator;
import org.tbeerbower.model.UserGame;
import org.tbeerbower.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerService {


    private final UserDao userDao;
    private final UserGameDao userGameDao;

    public PlayerService(UserDao userDao, UserGameDao userGameDao) {
        this.userDao = userDao;
        this.userGameDao = userGameDao;
    }

    public User getPlayer(View view) {
        try {
            view.displayDivider();
            view.display("Enter username: ");
            String username = view.getUserString();
            User user = userDao.getUserByUsername(username);
            if (user == null) {
                view.displayLine(String.format("Creating new user %s.", username));
                view.display("Create password: ");
                String password = view.getUserString();
                user = userDao.createUser(username, password, "ROLE_USER");
            } else {

                // TODO : Authenticate the user
            }
            view.displayLine(String.format("Hello! Logged in as %s.", username));
            return user;
        } catch (DaoException e) {
            view.displayLine("Can't get user: " + e.getMessage());
           return null;
        }
    }

    public void displayPlayers(View view, Collection<User> userList, boolean showNumbers) {
        view.displayDivider();
        view.displayLine(String.format("%sPlayer               Wins   Losses  Average Score",
                showNumbers ? "      " : ""));
        int i = 1;
        for (User user : userList) {
            String line = showNumbers ?
                    String.format("%-5d %s", i++, user.toString()) :
                    user.toString();
            view.displayLine(line);
        }
    }
    public void displayPlayers(View view) {
        displayPlayers(view, getPlayerList(), false);
    }

    public void displayPlayers(View view, UserComparator.Mode mode) {
        List<User> playersList = getPlayerList();
        playersList.sort(new UserComparator(mode));
        displayPlayers(view, playersList, true);
    }

    private List<User> getPlayerList() {
        Map<Integer, User> playerMap = new HashMap<>();

        List<UserGame> userGames = userGameDao.getUserGames();
        for (UserGame userGame : userGames) {
            int userId = userGame.getUserId();
            User user = playerMap.get(userId);
            if (user == null) {
                user = userDao.getUserById(userId);
                playerMap.put(userId, user);
            }
            user.addGame(userGame);
        }
        return new ArrayList<>(playerMap.values());
    }
}
