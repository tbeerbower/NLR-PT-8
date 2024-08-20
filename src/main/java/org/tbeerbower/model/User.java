package org.tbeerbower.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {

    @JsonIgnore
    private int id;
    private String username;
    private String displayName;
    private String profileImageUrl;
    private String shortBio;
    @JsonIgnore
    private boolean activated = true;
    private List<UserGame> games = new ArrayList<>();

    // User roles
    private Set<Authority> authorities = new HashSet<>();

    private String password;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public List<UserGame> getGames() {
        return games;
    }

    public void setGames(List<UserGame> games) {
        this.games = games;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setAuthorities(String authorities) {
        String[] roles = authorities.split(",");
        for(String role : roles) {
            String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
            this.authorities.add(new Authority(authority));
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void addGame(UserGame game) {
        games.add(game);
    }

    public int getWins() {
        int wins = 0;
        for (UserGame game : games) {
            if (game.isWin()) {
                ++wins;
            }
        }
        return wins;
    }

    public int getLosses() {
        int losses = 0;
        for (UserGame game : games) {
            if (game.isLoss()) {
                ++losses;
            }
        }
        return losses;
    }

    public double getAverageScore() {
        int totalGuesses = 0;
        int gamesPlayed = 0;
        for (UserGame game : games) {
            if (game.isWin()) {
               totalGuesses += game.getGuesses().size();
               ++gamesPlayed;
            } else if(game.isLoss()) {
                totalGuesses += WordleUserGame.TOTAL_GUESSES_FOR_LOSS;
                ++gamesPlayed;
            }
        }
        return gamesPlayed == 0 ? 0 : totalGuesses / (double) gamesPlayed;
    }

    @Override
    public String toString() {
        return String.format("%-15.15s  %5d  %5d      %5.2f",
                getUsername(), getWins(), getLosses(), getAverageScore());
    }
}
