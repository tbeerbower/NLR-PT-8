package org.tbeerbower.model;

public enum GameType {
    WORDLE,
    WORDLE_PEAKS;

    public String toValue() {
        return name();
    }
}
