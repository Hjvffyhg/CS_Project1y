
package com.mycompany.colortapswitchmain;

public class PlayerInfo {

    private String playerName;
    private int playerScore;

    public PlayerInfo(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }
}
