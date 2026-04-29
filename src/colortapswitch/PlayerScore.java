package colortapswitch;

public class PlayerScore {

    private String playerName;
    private int score;
    private final ScoreFileWriter scoreFileWriter = new ScoreFileWriter();

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void saveScoreToFile() {
        String scoreEntry = playerName + " - " + score + System.lineSeparator();
        scoreFileWriter.appendScore(scoreEntry);
    }
}
