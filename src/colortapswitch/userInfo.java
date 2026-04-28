package colortapswitch;

import java.nio.file.*;
import java.io.IOException;

public class userInfo {

    public String user;
    public int userScore;

    FileWritingClass fwc = new FileWritingClass();

    public void setUsername(String usern) {
        this.user = usern;
    }

    public void setScore(int s) {
        this.userScore = s;
    }

    public String getUsername() {
        return user;
    }

    public int getScore() {
        return userScore;
    }

    // Call this ONCE when the game ends
    public void saveUserInfoToFile() {
        String entry = this.user + " - " + this.userScore + System.lineSeparator();
        fwc.fileWriteIt(entry);
    }
}
